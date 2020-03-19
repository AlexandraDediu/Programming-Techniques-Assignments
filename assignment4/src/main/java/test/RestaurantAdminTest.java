package test;

import java.io.IOException;
import java.util.Collection;

import restaurant.IRestaurantProcessing;
import restaurant.InvalidStateException;
import restaurant.Restaurant;
import restaurant.model.BaseProduct;
import restaurant.model.CompositeProduct;
import restaurant.model.MenuItem;

public class RestaurantAdminTest {

	private static IRestaurantProcessing restaurantProcessing;

	public static void main(String[] args) throws ClassNotFoundException, IOException, InvalidStateException {
		restaurantProcessing = new Restaurant();
		RestaurantAdminTest rt = new RestaurantAdminTest();
		rt.testMenuAdd();
		rt.testMenuDelete();
		rt.testMenuEdit();
	}

	public void testMenuAdd() throws ClassNotFoundException, IOException, InvalidStateException {
		MenuItem item1 = new BaseProduct("Ursus", 5);
		// create composite product
		MenuItem item2 = new CompositeProduct("Pizza");
		item2.add(new BaseProduct("Cheese", 5));
		item2.add(new BaseProduct("Tomato sauce", 2));
		restaurantProcessing.createNewMenuItem(item1);
		restaurantProcessing.createNewMenuItem(item2);
//
//		try {
//			restaurantProcessing.saveMenu();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		Collection<MenuItem> saved = null;
//		try {
//			saved = restaurantProcessing.loadMenu();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println("Loaded: " + saved.size() + " items");
	}

	public void testMenuDelete() throws ClassNotFoundException, IOException, InvalidStateException {
		MenuItem item1 = new BaseProduct("Ursus", 5);
		restaurantProcessing.deleteMenuItem(item1);

//		try {
//			restaurantProcessing.saveMenu();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	

	public void testMenuEdit() throws ClassNotFoundException, IOException, InvalidStateException {
		MenuItem item2 = new CompositeProduct("Pizza");
		item2.add(new BaseProduct("Cheese", 5));
		item2.add(new BaseProduct("Tomato", 1));

		restaurantProcessing.editMenuItem(item2);

//		try {
//			restaurantProcessing.saveMenu();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
	
