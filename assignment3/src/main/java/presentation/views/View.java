package presentation.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;



import model.Customer;
import model.Product;
import presentation.Controller;
import presentation.views.add.AddCustomerView;
import presentation.views.add.AddOrderView;
import presentation.views.add.AddProductView;
import presentation.BillGenarator;

public class View {
   

    private final Controller controller = new Controller();;
    private JFrame mainFrame;
    private JButton addCustomerButton;
    private JButton showCustomersButton;
    private JButton addOrderButton;
    private JButton addProductButton;
    private JButton showProductsButton;
    private JTable table;
  
   

    private HashMap<Integer, Vector> modifiedRows;
    private Vector rowToDelete;
    

    public View() {
        mainFrame = new JFrame();
        addCustomerButton = new JButton("Add Customer");
        addCustomerButton.setBounds(80, 100, 130, 40);
        //addCustomerButton.setSize(new Dimension(50, 50));
        addCustomerButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                AddCustomerView addCustomerView = new AddCustomerView(controller);
                addCustomerView.drawView();
                
            }
        });

        modifiedRows = new HashMap<Integer, Vector>();

        showCustomersButton = new JButton("Show Customers");
        showCustomersButton.setBounds(80, 160, 180, 40);
        showCustomersButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                JButton button = new JButton("Save changes");
                button.setBounds(1100, 250, 130, 50);
                button.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        for (Integer rowIndex : modifiedRows.keySet()) {
                            Vector row = modifiedRows.get(rowIndex);
                            
                            String age="";
                            if(row.get(4) instanceof String) {
                            	age=(String) row.get(4);
                             }
                            else {
                            	age=Integer.toString((Integer)row.get(4));
                            }
                            Customer c = new Customer((Integer) row.get(0), (String) row.get(1), (String) row.get(2),
                                    (String) row.get(3), Integer.valueOf( age));
                            controller.updateCustomer(c);
                        }
                        redrawTable(true);
                    }
                });
                mainFrame.add(button);

                JButton deleteButton = new JButton("Delete selected");
                deleteButton.setBounds(1100, 300, 130, 50);
                deleteButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                    	String age="";
                        if(rowToDelete.get(4) instanceof String) {
                        	age=(String)rowToDelete.get(4);
                         }
                        else {
                        	age=Integer.toString((Integer)rowToDelete.get(4));
                        }
                        Customer c = new Customer((Integer) rowToDelete.get(0), (String) rowToDelete.get(1), (String)rowToDelete.get(2),
                                (String) rowToDelete.get(3), Integer.valueOf( age));
                        
                        try {
                        	controller.deleteCustomer(c);
                        	redrawTable(false);
                        	
                        }catch(IllegalArgumentException ex) {
                        	JOptionPane.showMessageDialog(mainFrame, ex.getMessage());
                        }
                       
                       
                    }
                });
                mainFrame.add(button);
                mainFrame.add(deleteButton);
                redrawTable(true);
            }
        });
        

        addOrderButton = new JButton("Add Order");
        addOrderButton.setBounds(550, 100, 130, 40);
        addOrderButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                AddOrderView addOrderView = new AddOrderView(controller);
                addOrderView.drawView();
            }
        });
        

        addProductButton = new JButton("Add Product");
        addProductButton.setBounds(315, 100, 130, 40);
        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddProductView addProductView = new AddProductView(controller);
                addProductView.drawView();
                //table.repaint();
            }
        });
        
      
        showProductsButton = new JButton("Show Products");
        showProductsButton.setBounds(315, 160, 180, 40);
        showProductsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ev) {
                
                JButton button = new JButton("Save changes");
                button.setBounds(1100, 250, 130, 50);
                button.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        for (Integer rowIndex : modifiedRows.keySet()) {
                            Vector row = modifiedRows.get(rowIndex);
                            
                            String price="";
                            if(row.get(4) instanceof String) {
                            	price=(String) row.get(4);
                             }
                            else {
                            	price=Float.toString((Float)row.get(4));
                            }
                            
                            String quantity="";
                            if(row.get(3) instanceof String) {
                            	quantity=(String) row.get(3);
                             }
                            else {
                            	quantity=Integer.toString((Integer)row.get(4));
                            }
                            
                            Product p = new Product((Integer) row.get(0), (String) row.get(1), (String) row.get(2),
                                    Integer.valueOf(quantity), Float.valueOf(price));
                            controller.updateProduct(p);
                        }
                        redrawTable(false);
                    }
                });
                mainFrame.add(button);

                JButton deleteButton = new JButton("Delete selected");
                deleteButton.setBounds(1100, 300, 130, 50);
                deleteButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        Product p= new Product((Integer) rowToDelete.get(0), (String) rowToDelete.get(1),
                                (String) rowToDelete.get(2), (Integer) rowToDelete.get(3), (Float) rowToDelete.get(4));
                        try {
                        	controller.deleteProduct(p);
                        	redrawTable(false);
                        	
                        }catch(IllegalArgumentException ex) {
                        	JOptionPane.showMessageDialog(mainFrame, ex.getMessage());
                        }
                       
                    
                    }
                });
                mainFrame.add(button);
                mainFrame.add(deleteButton);
                redrawTable(false);
            }
        });
        


        mainFrame.add(addCustomerButton);
        mainFrame.add(addOrderButton);
        mainFrame.add(addProductButton);
        mainFrame.add(showCustomersButton);
        mainFrame.add(showProductsButton);
       
    }

    public void drawMainView() {
        mainFrame.setSize(1270, 800);
        mainFrame.setLayout(null);// using no layout managers
        mainFrame.setVisible(true);// making the frame visible
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void redrawTable(boolean isCustomerTable) {
    
        if (mainFrame.isAncestorOf(table)) {
            mainFrame.remove(table);
            
        }
        if(isCustomerTable) {
        table = createTable(controller.getAllCustomers());}
        else {
        	 table = createTable(controller.getAllProducts());
        }
        mainFrame.add(table);
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }
     
    
    
    private JTable createTable(List<?> list) {
        try {
            if (list.size() > 0) {
                Object firstObject = list.get(0);
                Field[] objectFileds = firstObject.getClass().getDeclaredFields();
                Object[] header = new Object[objectFileds.length];
                for (int i = 0; i < objectFileds.length; i++) {
                    header[i] = objectFileds[i].getName();
                }
                Object[][] data = new Object[list.size() + 1][];
                data[0] = header;
                for (int i = 0; i < list.size(); i++) {
                    Object[] blankArray = new Object[objectFileds.length];
                    Field[] objectFields = ((Object) list.get(i)).getClass().getDeclaredFields();
                    for (int j = 0; j < objectFields.length; j++) {
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(objectFields[j].getName(),
                                ((Object) list.get(i)).getClass());
                        Method method = propertyDescriptor.getReadMethod();
                        blankArray[j] = method.invoke(list.get(i));
                    }
                    data[i + 1] = blankArray;
                }
                final DefaultTableModel model = new DefaultTableModel(data, header);
                model.addTableModelListener(new TableModelListener() {

                    public void tableChanged(TableModelEvent e) {
                        int row = e.getFirstRow();
                        Vector changedRow = (Vector) model.getDataVector().get(row);
                        modifiedRows.put(row, changedRow);
                    }
                });
                JTable table = new JTable(model);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                    public void valueChanged(ListSelectionEvent e) {
                        int row = e.getFirstIndex();
                        rowToDelete = (Vector) model.getDataVector().get(row);
                    }
                });
                table.setBounds(70, 250, 1000, 300);

                return table;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "error showing table");
        }

        return new JTable();
    }
    
}
