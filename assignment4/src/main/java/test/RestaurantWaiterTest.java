package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import restaurant.IRestaurantProcessing;
import restaurant.InvalidStateException;
import restaurant.Restaurant;
import restaurant.model.BaseProduct;
import restaurant.model.MenuItem;
import restaurant.model.Order;

public class RestaurantWaiterTest {
	private static IRestaurantProcessing restaurantProcessing;

	public static void main(String[] args) throws ClassNotFoundException, IOException, InvalidStateException {
		restaurantProcessing = new Restaurant();
		RestaurantWaiterTest rt = new RestaurantWaiterTest();
		rt.testMenuAdd();
	}
	
	public void testMenuAdd() throws ClassNotFoundException, IOException, InvalidStateException {
		MenuItem item1 = new BaseProduct("Ursus", 5);
		MenuItem item2 = new BaseProduct("Ursus", 5);
		Collection<MenuItem> orderItems = new ArrayList<MenuItem>();
		orderItems.add(item1);
		orderItems.add(item2);
		
		Order o = restaurantProcessing.createOrder(1, orderItems);
		restaurantProcessing.generateBill(o);
	}

}
