package bl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import restaurant.IRestaurantProcessing;
import restaurant.InvalidStateException;
import restaurant.model.BaseProduct;
import restaurant.model.CompositeProduct;
import restaurant.model.MenuItem;

public class AdminBusinessObject {
	private IRestaurantProcessing restaurantProcessing;

	public AdminBusinessObject(IRestaurantProcessing restaurantProcessing) {
		this.restaurantProcessing = restaurantProcessing; 
	}

	// Call this method with either ingredients(for CompositeProduct), or
	// price(BaseProduct)
	// Name is mandatory for both cases
	public void addMenuItem(String name, Map<String, Float> ingredients, float price) throws IOException, InvalidStateException {

		restaurantProcessing.createNewMenuItem(buildMenuItemObject(name, ingredients, price));
	}

	// for ingredients parameter, pass null for BaseProducts
	public void deleteMenuItem(String name, Map<String, Float> ingredients, float price)
			throws IOException, InvalidStateException {
		restaurantProcessing.deleteMenuItem(buildMenuItemObject(name, ingredients, price));
	}
	
	// for ingredients parameter, pass null for BaseProducts
	public void editMenuItem(String name, Map<String, Float> ingredients, float price)
			throws IOException, InvalidStateException {
		restaurantProcessing.editMenuItem(buildMenuItemObject(name, ingredients, price));
	}

	public MenuItem getMenuItemByName(String itemName) {
		for(MenuItem item : getmenu()) {
			if(item.getName().equals(itemName)) {
				return item;
			}
		}
		
		return null;
	}
	
	public HashSet<MenuItem> getmenu() {
		return (HashSet<MenuItem>) restaurantProcessing.getMenu();
	}
	
	private MenuItem buildMenuItemObject(String name, Map<String, Float> ingredients, float price) {
		MenuItem menuItem = null;
		if (ingredients != null) {
			menuItem = new CompositeProduct(name);
			for (String ingredient : ingredients.keySet()) {
				BaseProduct baseProd = new BaseProduct(ingredient, ingredients.get(ingredient));
				menuItem.add(baseProd);
			}
		} else {
			menuItem = new BaseProduct(name, price);
		}

		return menuItem;
	}
}
