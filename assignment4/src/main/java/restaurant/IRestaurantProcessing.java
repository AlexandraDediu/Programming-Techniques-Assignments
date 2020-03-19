package restaurant;

import java.io.IOException;
import java.util.Collection;

import restaurant.model.MenuItem;
import restaurant.model.Order;


public interface IRestaurantProcessing  {
	Collection<MenuItem> getMenu();
	/**
	 * Adds new menu item to the menu
	 * @pre newMenuItem != null
	 * @post menu.size() == menu@pre.size() + 1  
	 */
	void createNewMenuItem(MenuItem newMenuItem) throws IOException, InvalidStateException;  
	
	/**
	 * Deletes an existing item from the menu
	 * @pre menu.size() > 0 && menuItem != null && menu.contains(menuItem)
	 * @post menu.size() == menu@pre.size() - 1
	 * 
	 **/
	void deleteMenuItem(MenuItem menuItem) throws IOException, InvalidStateException;
	
	/**
	 * Modifies an existing menu item
	 * @pre menu.size() > 0 && menu.contains(menuItem) && menuItem != null
	 * @post menu.size() == menu@pre.size()
	 */
	void editMenuItem(MenuItem menuItem) throws IOException, InvalidStateException;
	
	/**
	 * Creates an order
	 * @pre orderId != null && table > 0 && orderItems.size() > 0
	 * @post orders.contains(order) == true && orders.getsize() == orders@pre.getSize() + 1
	 */
	Order createOrder(int table, Collection<MenuItem> orderItems) throws InvalidStateException;
	
	/**
	 * Computes the price of a given order
	 * @return the price of the order
	 * @pre order != null
	 */
	float computeOrderPrice(Order order);
	
	/**
	 * Generates the bill
	 * 
	 * @pre order != null && orders.get(order).size() > 0
	 */
	void generateBill(Order order) throws IOException;
	
	//void saveMenu() throws IOException;
	
	//Collection<MenuItem> loadMenu() throws ClassNotFoundException, IOException;
	
}
