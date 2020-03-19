package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import bl.AdminBusinessObject;
import restaurant.model.BaseProduct;
import restaurant.model.CompositeProduct;
import restaurant.model.MenuItem;

public class AdminView {
	private JFrame view;
	private JTable table;
	private JButton addBaseMenuItem;
	private JButton addComposedMenuItem;
	private JButton deleteMenuItem;
	private JButton editMenuItem;
	private AdminBusinessObject admin;
	private Vector selectedRow;
	


	public AdminView(AdminBusinessObject a) {
		this.admin = a;
	}

	public void draw() {
		view = new JFrame();
		
		addBaseMenuItem = new JButton("New Base Item");
		addBaseMenuItem.setBounds(70, 100, 150, 40);
		addBaseMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				final JFrame newBaseProductView = new JFrame();
				newBaseProductView.setSize(400, 400);
				newBaseProductView.setLayout(null);// using no layout managers
				newBaseProductView.setVisible(true);// making the frame visible

				JButton save = new JButton("Save");
				save.setBounds(70, 30, 70, 40);

				JLabel nameLbl = new JLabel("Name");
				nameLbl.setBounds(70, 130, 70, 40);
				final JTextField nameTxt = new JTextField("nameTxt");
				nameTxt.setBounds(150, 130, 70, 40);

				JLabel priceLabel = new JLabel("Price");
				priceLabel.setBounds(70, 200, 70, 40);
				final JTextField priceTxt = new JTextField("priceTxt");
				priceTxt.setBounds(150, 200, 70, 40);

				save.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						try {
							admin.addMenuItem(nameTxt.getText(), null, Float.parseFloat(priceTxt.getText()));
							newBaseProductView.dispatchEvent(new WindowEvent(newBaseProductView, WindowEvent.WINDOW_CLOSING));
							redrawTable();
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(newBaseProductView, "Invalid input");
						}
					}
				});
				newBaseProductView.add(save);
				newBaseProductView.add(nameLbl);
				newBaseProductView.add(nameTxt);
				newBaseProductView.add(priceLabel);
				newBaseProductView.add(priceTxt);

			}
		});
		
		addComposedMenuItem = new JButton("New Composed Item");
		addComposedMenuItem.setBounds(300, 100, 150, 40);
		addComposedMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				final JFrame addCompositeProd = new JFrame();
				addCompositeProd.setSize(400, 400);
				addCompositeProd.setLayout(null);// using no layout managers
				addCompositeProd.setVisible(true);// making the frame visible

				JButton save = new JButton("Save");
				save.setBounds(70, 30, 70, 40);
				
				JLabel nameLbl = new JLabel("Name");
				nameLbl.setBounds(70, 130, 70, 40);
				final JTextField nameTxt = new JTextField("nameTxt");
				nameTxt.setBounds(150, 130, 70, 40);
				addCompositeProd.add(nameLbl);
				addCompositeProd.add(nameTxt);

				JLabel ingredLb = new JLabel("Ingredients");
				ingredLb.setBounds(70, 170, 170, 40);
				addCompositeProd.add(ingredLb);

				int idx = 0;
				final List<JTextField> ingredients = new ArrayList<JTextField>();
				
				for (int i=0; i<2; i++) {
					
					final JTextField ingredTxt = new JTextField("Name-" + i);
					ingredTxt.setText("name");
					ingredTxt.setBounds(70, 200 + idx * 50, 70, 40);
					ingredients.add(ingredTxt);

					final JTextField priceTxt = new JTextField("price");
					priceTxt.setText("price");
					priceTxt.setBounds(150, 200 + idx * 50, 70, 40);
					ingredients.add(priceTxt);

					idx++;
					addCompositeProd.add(ingredTxt);
					addCompositeProd.add(priceTxt);
				}
				

				JButton addIngr = new JButton("Add Ingredient");
				addIngr.setBounds(150, 30, 70, 40);
				addIngr.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						int lastY = (int)ingredients.get(ingredients.size() - 1 ).getBounds().getMaxY();
						final JTextField ingredTxt = new JTextField("Name");
						ingredTxt.setBounds(70, lastY + 10, 70, 40);
						ingredients.add(ingredTxt);

						final JTextField priceTxt = new JTextField("Price");
						priceTxt.setBounds(150, lastY + 10, 70, 40);
						ingredients.add(priceTxt);
						
						addCompositeProd.add(ingredTxt);
						addCompositeProd.add(priceTxt);
						
					}
				});
				addCompositeProd.add(addIngr);
				
				save.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						try {
							Map<String, Float> baseComponents = new HashMap<String, Float>();
							for (int i = 0; i < ingredients.size() - 1; i = i + 2) {
								baseComponents.put(ingredients.get(i).getText(),
										Float.parseFloat(ingredients.get(i + 1).getText()));
							}
							admin.addMenuItem(nameTxt.getText(), baseComponents, 0.0f);
							addCompositeProd.dispatchEvent(new WindowEvent(addCompositeProd, WindowEvent.WINDOW_CLOSING));
							redrawTable();
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(addCompositeProd, "Invalid input");
						}
					}
				});
				addCompositeProd.add(save);


			}
		});
		
		view.add(addComposedMenuItem);

		editMenuItem = new JButton("Edit Menu Item");
		editMenuItem.setBounds(500, 100, 150, 40);
		// se face enable la selectia unui rand din meniu
		editMenuItem.setEnabled(false);
		editMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean isBaseProduct;
				if (selectedRow.get(2) instanceof String) {
					isBaseProduct = Boolean.parseBoolean((String) selectedRow.get(2));
				} else {
					isBaseProduct = (Boolean) selectedRow.get(2);
				}
				MenuItem menuItem = admin.getMenuItemByName((String) selectedRow.get(0));

				if (menuItem instanceof BaseProduct) {
					final JFrame editView = new JFrame();
					editView.setSize(400, 400);
					editView.setLayout(null);// using no layout managers
					editView.setVisible(true);// making the frame visible

					JButton save = new JButton("Save");
					save.setBounds(70, 30, 70, 40);

					JLabel nameLbl = new JLabel("Name");
					nameLbl.setBounds(70, 130, 70, 40);
					final JTextField nameTxt = new JTextField("nameTxt");
					nameTxt.setText(menuItem.getName());
					nameTxt.setBounds(150, 130, 70, 40);

					JLabel priceLabel = new JLabel("Price");
					priceLabel.setBounds(70, 200, 70, 40);
					final JTextField priceTxt = new JTextField("priceTxt");
					priceTxt.setText(Float.toString(menuItem.computePrice()));
					priceTxt.setBounds(150, 200, 70, 40);

					save.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							try {
								admin.editMenuItem(nameTxt.getText(), null, Float.parseFloat(priceTxt.getText()));
								editView.dispatchEvent(new WindowEvent(editView, WindowEvent.WINDOW_CLOSING));
								redrawTable();
							} catch (Exception ex) {
								ex.printStackTrace();
								JOptionPane.showMessageDialog(editView, "Invalid input");
							}
						}
					});
					editView.add(save);
					editView.add(nameLbl);
					editView.add(nameTxt);
					editView.add(priceLabel);
					editView.add(priceTxt);

				} else {
					final JFrame editView = new JFrame();
					editView.setSize(400, 400);
					editView.setLayout(null);// using no layout managers
					editView.setVisible(true);// making the frame visible

					JButton save = new JButton("Save");
					save.setBounds(70, 30, 70, 40);
					
					

					CompositeProduct cp = (CompositeProduct) menuItem;
					JLabel nameLbl = new JLabel("Name");
					nameLbl.setBounds(70, 130, 70, 40);
					final JTextField nameTxt = new JTextField("nameTxt");
					nameTxt.setText(menuItem.getName());
					nameTxt.setBounds(150, 130, 70, 40);
					editView.add(nameLbl);
					editView.add(nameTxt);

					JLabel ingredLb = new JLabel("Ingredients");
					ingredLb.setBounds(1300, 130, 70, 40);
					editView.add(ingredLb);

					int idx = 0;
					final List<JTextField> ingredients = new ArrayList<JTextField>();
					for (MenuItem cpItem : cp.getItems()) {
						final JTextField ingredTxt = new JTextField(cp.getName() + "Name");
						ingredTxt.setText(cpItem.getName());
						ingredTxt.setBounds(70, 200 + idx * 50, 70, 40);
						ingredients.add(ingredTxt);

						final JTextField priceTxt = new JTextField(cp.getName() + "Price");
						priceTxt.setText(Float.toString(cpItem.computePrice()));
						priceTxt.setBounds(150, 200 + idx * 50, 70, 40);
						ingredients.add(priceTxt);

						idx++;
						editView.add(ingredTxt);
						editView.add(priceTxt);
					}

					JButton addIngr = new JButton("Add Ingredient");
					addIngr.setBounds(150, 30, 70, 40);
					addIngr.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							int lastY = (int)ingredients.get(ingredients.size() - 1 ).getBounds().getMaxY();
							final JTextField ingredTxt = new JTextField("Name");
							ingredTxt.setBounds(70, lastY + 10, 70, 40);
							ingredients.add(ingredTxt);

							final JTextField priceTxt = new JTextField("Price");
							priceTxt.setBounds(150, lastY + 10, 70, 40);
							ingredients.add(priceTxt);
							
							editView.add(ingredTxt);
							editView.add(priceTxt);
							
						}
					});
					editView.add(addIngr);
					
					save.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							try {
								Map<String, Float> baseComponents = new HashMap<String, Float>();
								for (int i = 0; i < ingredients.size() - 1; i = i + 2) {
									baseComponents.put(ingredients.get(i).getText(),
											Float.parseFloat(ingredients.get(i + 1).getText()));
								}
								admin.editMenuItem(nameTxt.getText(), baseComponents, 0.0f);
								editView.dispatchEvent(new WindowEvent(editView, WindowEvent.WINDOW_CLOSING));
								redrawTable();
							} catch (Exception ex) {
								ex.printStackTrace();
								JOptionPane.showMessageDialog(editView, "Invalid input");
							}
						}
					});
					editView.add(save);

				}
			}
		});

		deleteMenuItem = new JButton("Delete Menu Item");
		deleteMenuItem.setBounds(700, 100, 150, 40);
		deleteMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MenuItem menuItem = admin.getMenuItemByName((String) selectedRow.get(0));
				try {
					admin.deleteMenuItem((String) selectedRow.get(0), null, 0.0f);
					redrawTable();
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(view, "Invalid input");
				}
			}
		});
		// se face enable la selectia unui rand din meniu
		deleteMenuItem.setEnabled(false);
		
		view.add(addBaseMenuItem);
		view.add(addComposedMenuItem);
		view.add(editMenuItem);
		view.add(deleteMenuItem);
		redrawTable();
		view.setSize(950, 600);
		view.setLayout(null);// using no layout managers
		view.setVisible(true);// making the frame visible
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	
	
	
	public void redrawTable() {
		if (view.isAncestorOf(table)) {
			view.remove(table);

		}
		table = createTable(admin.getmenu());
		view.add(table);
		SwingUtilities.updateComponentTreeUI(view);
	}

	private JTable createTable(HashSet<MenuItem> menu) {
		if (menu.size() > 0) {
			Object[] header = { "Name", "Price", "Base Product" };
			Object[][] data = new Object[menu.size() + 1][];
			data[0] = header;
			MenuItem[] array = new MenuItem[menu.size()];
			menu.toArray(array);
			for (int i = 0; i < array.length; i++) {
				Object[] menuRow = new Object[3];
				MenuItem item = array[i];
				menuRow[0] = item.getName();
				menuRow[1] = item.computePrice();
				if (item instanceof BaseProduct) {
					menuRow[2] = true;

				} else {
					menuRow[2] = false;
				}
				data[i + 1] = menuRow;
			}
			final DefaultTableModel model = new DefaultTableModel(data, header);
			JTable table = new JTable(model);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent e) {
					selectedRow = (Vector) model.getDataVector().get(e.getFirstIndex());
					// enable edit and delete actions
					editMenuItem.setEnabled(true);
					deleteMenuItem.setEnabled(true);
				}
			});
			table.setBounds(70, 150, 600, 300);

			return table;
		}
		return null;
	}
}
