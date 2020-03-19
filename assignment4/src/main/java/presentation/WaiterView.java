package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import bl.WaiterBusinessObject;
import restaurant.InvalidStateException;
import restaurant.model.BaseProduct;
import restaurant.model.MenuItem;
import restaurant.model.Order;

public class WaiterView {
	private JFrame view;
	private JTable table;
	private WaiterBusinessObject waiter;
	private Vector selectedRow;
	private JButton addToOrder;
	private ArrayList<MenuItem> currentOrderItems;

	public WaiterView(WaiterBusinessObject w) {
		waiter = w;
		currentOrderItems = new ArrayList<MenuItem>();
	}

	public void draw() {
		view = new JFrame();

		JLabel tableLabel = new JLabel("Table");
		tableLabel.setBounds(700, 100, 70, 40);
		final JTextField tableTxt = new JTextField("tableTxt");
		tableTxt.setBounds(800, 100, 70, 40);

		final JTextArea textArea = new JTextArea();
		textArea.setBounds(700, 150, 400, 300);

		addToOrder = new JButton("Add to order");
		addToOrder.setBounds(70, 100, 100, 40);
		addToOrder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				boolean isBaseProduct;
				if (selectedRow.get(2) instanceof String) {
					isBaseProduct = Boolean.parseBoolean((String) selectedRow.get(2));
				} else {
					isBaseProduct = (Boolean) selectedRow.get(2);
				}
				MenuItem menuItem = waiter.getMenuItemByName((String) selectedRow.get(0));
				textArea.setText(textArea.getText() + "\n" + menuItem.getName());
				currentOrderItems.add(menuItem);
				//selectedRow = null;
			}
		});

		JTable table = createTable(waiter.getmenu());

		JButton save = new JButton("Save and Generate Bill");
		save.setBounds(200, 100, 100, 40);
		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					Integer tableNumber = Integer.parseInt(tableTxt.getText());
					Order o = waiter.addOrder(tableNumber, currentOrderItems);
					
					waiter.generateBill(o.getOrderId(), o.getOrderDate(), o.getTable());
					ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "bill_" + o.getOrderId() + ".txt");
					pb.start();
					
					textArea.setText("");
					currentOrderItems.removeAll(currentOrderItems);
				} catch (Exception e1) {

					e1.printStackTrace();
					JOptionPane.showMessageDialog(view, "Invalid input");
				}
			}
		});

		
		view.add(tableTxt);
		view.add(tableLabel);
		view.add(addToOrder);
		view.add(save);
		view.add(table);
		view.add(textArea);
		view.setSize(950, 600);
		view.setLayout(null);// using no layout managers
		view.setVisible(true);// making the frame visible
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void drawTable() {

		table = createTable(waiter.getmenu());
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
			final JTable table = new JTable(model);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@SuppressWarnings("rawtypes")
				public void valueChanged(ListSelectionEvent e) {
					selectedRow = (Vector) model.getDataVector().get(table.getSelectedRow());

				}
			});
			table.setBounds(70, 150, 600, 300);

			return table;
		}
		return null;
	}
}
