package bl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import restaurant.IRestaurantProcessing;
import restaurant.InvalidStateException;
import restaurant.model.MenuItem;
import restaurant.model.Order;

public class WaiterBusinessObject {
	private IRestaurantProcessing restaurantProcessing;

	public WaiterBusinessObject(IRestaurantProcessing restaurantProcessing) {
		this.restaurantProcessing = restaurantProcessing;
		
	}
	
	public HashSet<MenuItem> getmenu() {
		return (HashSet<MenuItem>) restaurantProcessing.getMenu();
	}
	
	public Order addOrder(int table, Collection<MenuItem> orderItems) throws InvalidStateException {
		Order o = restaurantProcessing.createOrder(table, orderItems);
		return o;
	}
	
	public void generateBill(int orderId, Date date, int table) throws IOException {
		restaurantProcessing.generateBill(new Order(orderId, date, table));
	}
	
	public MenuItem getMenuItemByName(String itemName) {
		for(MenuItem item : getmenu()) {
			if(item.getName().equals(itemName)) {
				return item;
			}
		}
		
		return null;
	}
	
	
}
