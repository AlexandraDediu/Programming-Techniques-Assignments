
package restaurant;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import restaurant.IRestaurantProcessing;
import restaurant.model.MenuItem;
import restaurant.model.Order;
import java.util.Observable;

import javax.net.ssl.ExtendedSSLSession;

/**
 * 
 * @invariant isWellFormed()
 * 
 */

public class Restaurant  extends Observable  implements IRestaurantProcessing  {

	private HashSet<MenuItem> menu;
	private Map<Order, Collection<MenuItem>> orders;
	private int nextOrderId;

	public Restaurant() throws ClassNotFoundException, IOException {
		nextOrderId = 1;
		menu = loadMenu();
		orders = new HashMap<Order, Collection<MenuItem>>();
	}

	public boolean isWellFormed() throws InvalidStateException {
		// validate all items for ongoing orders are in the menu
		boolean isWellFormed = true;

		for (Order order : orders.keySet()) {
			Collection<MenuItem> orderItems = orders.get(order); //aici imi iau valorile pe baza cheilor(adica produsele din comanda)
			if (orderItems.size() < 1) {
				isWellFormed = false;
				break;
			}
			for (MenuItem item : orderItems) {
				if (!menu.contains(item)) {
					isWellFormed = false;
					break;
				}
			}
		}
		return isWellFormed;
	}

	public Collection<MenuItem> getMenu() {
		return menu;
	}

	public void createNewMenuItem(MenuItem newMenuItem) throws IOException, InvalidStateException {
		assert newMenuItem != null : "Menu item cannot be null";

		int preCreateMenuSize = menu.size();
		menu.add(newMenuItem);
		int postCreateMenuSize = menu.size();

		assert preCreateMenuSize + 1 == postCreateMenuSize : "Create menu item unsuccessful";
		if (!isWellFormed())
			throw new InvalidStateException();
		saveMenu();
	}

	public void deleteMenuItem(MenuItem menuItem) throws IOException, InvalidStateException {
		assert menuItem != null : "Menu item to delete cannot be null";
		assert menu.contains(menuItem) : "Menu doesn't contain this menu item.";

		int preDeleteMenuSize = menu.size();
		menu.remove(menuItem);
		int postDeleteMenuSize = menu.size();

		assert preDeleteMenuSize - 1 == postDeleteMenuSize : "Delete menu item unsuccessful";

		if (!isWellFormed())
			throw new InvalidStateException();
		saveMenu();

	}

	public void editMenuItem(MenuItem menuItem) throws IOException, InvalidStateException {
		assert menuItem != null : "Menu item to edit cannot be null";
		boolean exists = false;
		for (MenuItem menuItems : menu) {
			if (menuItems.getName().equals(menuItem.getName())) {
				exists = true;
				break;
			}
		}
		assert exists : "Cannot edit menu item as it does not exist";

		int preEditMenuSize = menu.size();
		menu.remove(menuItem);
		menu.add(menuItem);

		assert preEditMenuSize == menu.size() : "Edit did not preserve the menu size";
		if (!isWellFormed())
			throw new InvalidStateException();

		saveMenu();
	}

	public Order createOrder(int table, Collection<MenuItem> orderItems) throws InvalidStateException {
		assert table > 0 && orderItems.size() > 0 : "Invalid input";
		boolean allItemsInMenu = true;
		for (MenuItem orderItem : orderItems) {
			if (!menu.contains(orderItem)) {
				allItemsInMenu = false;
				break;
			}
		}
		assert allItemsInMenu : "Item not in menu";

		int preCreateOrdersSize = orders.keySet().size();
		Order newOrder = new Order(nextOrderId, new Date(), table);
		orders.put(newOrder, orderItems);
		nextOrderId++;

		assert orders.get(newOrder) != null : "Failed to add order";
		assert orders.keySet().size() == preCreateOrdersSize + 1 : "Failed to add order";

		if (!isWellFormed())
			throw new InvalidStateException();
		
		setChanged();
		notifyObservers(orderItems);  //aici notific observerii(cheful), atunci cand se face o comanda noua
        clearChanged();
        
		return newOrder;
	}

	public float computeOrderPrice(Order order) {
		
		assert order!=null && orders.get(order) != null : "Invalid Order";
		float orderPrice = 0f;
		for (MenuItem orderItem : orders.get(order)) {
			orderPrice += orderItem.computePrice();
		}
		
		assert orderPrice > 0 : "Fail to compute orderPrice";
		return orderPrice;
	}

	public void generateBill(Order order) throws IOException {
	
		assert order!=null && orders.get(order) != null && orders.get(order).size() > 0 :"Invalid Order";
		
		FileWriter fileWriter = new FileWriter("bill_" + order.getOrderId() + ".txt");
		BufferedWriter writer = new BufferedWriter(fileWriter);
		writer.write("Order: " + order.getOrderId());
		writer.write(System.getProperty("line.separator"));
		writer.write("Date: " + order.getOrderDate());
		writer.write(System.getProperty("line.separator"));
		writer.write("Table: " + order.getTable());
		writer.write(System.getProperty("line.separator"));
		for (MenuItem orderItem : orders.get(order)) {
			writer.write(orderItem.getName() + ": " + orderItem.computePrice());
			writer.write(System.getProperty("line.separator"));
		}
		writer.write("Total: " + computeOrderPrice(order));
		writer.flush();
		writer.close();
		fileWriter.close();

		// remove order
		//orders.remove(order);
	}

	private void saveMenu() throws IOException {
		// do not append to file, instead overwrite it
		FileOutputStream fout = new FileOutputStream("menu.ser", false);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(this.menu);
		oos.close();
		fout.close();
	}

	private HashSet<MenuItem> loadMenu() throws ClassNotFoundException, IOException {
		try {
			FileInputStream fileIn = new FileInputStream("menu.ser");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);

			Object obj = objectIn.readObject();
			objectIn.close();
			fileIn.close();

			return (HashSet<MenuItem>) obj; //conversie explicita
		} catch (FileNotFoundException e) {
			return new HashSet<MenuItem>();
		}
	}

}
