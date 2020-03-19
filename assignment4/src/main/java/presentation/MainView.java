package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import bl.AdminBusinessObject;
import bl.WaiterBusinessObject;
import restaurant.IRestaurantProcessing;
import restaurant.Restaurant;

public class MainView {
	
	
	private static IRestaurantProcessing restaurantProcessing;
	private static AdminBusinessObject admin;
	private static WaiterBusinessObject waiter;
	private static ChefView chefObserver;
	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		restaurantProcessing = new Restaurant();
		chefObserver = new ChefView();
		((Restaurant)restaurantProcessing).addObserver(chefObserver);
		admin = new AdminBusinessObject(restaurantProcessing);
		waiter = new WaiterBusinessObject(restaurantProcessing);
		
		JFrame mainFrame = new JFrame();
		JButton waiterView = new JButton("Waiter");
		waiterView.setBounds(70, 100, 100, 40);
		waiterView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              
                WaiterView waiterView=new WaiterView(waiter);
                waiterView.draw();
            }
        });
		
		JButton adminButton = new JButton("Admin");
		adminButton.setBounds(270, 100, 100, 40);
		adminButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
        		AdminView adminView = new AdminView(admin);
        		adminView.draw();
            }
        });
		
		JButton chefView = new JButton("Chef");
		chefView.setBounds(470, 100, 100, 40);
		chefView.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
              
            	chefObserver.draw();
            	
            }
        });
		
		mainFrame.add(waiterView);
        mainFrame.add(adminButton);
        mainFrame.add(chefView);
        mainFrame.setTitle("Restaurant");
        mainFrame.setSize(650, 300);
        mainFrame.setLayout(null);// using no layout managers
        mainFrame.setVisible(true);// making the frame visible
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
