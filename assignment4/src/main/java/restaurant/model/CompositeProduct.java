package restaurant.model;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeProduct implements MenuItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
    private ArrayList<MenuItem> items;
    
    public CompositeProduct() {}
    
    public CompositeProduct(String name) {
    	this.name = name;
    	items = new ArrayList<MenuItem>();
    }
    
    public void add(MenuItem item) {
    	items.add(item);
    }
    
    public void remove(MenuItem item) {
    	items.remove(item);
    }

    public Collection<MenuItem> getItems() {
    	return items;
    }
    
	public float computePrice() {
		float compositePrice = 0f;
		for(MenuItem item : items) {
			compositePrice += item.computePrice();
		}
		return compositePrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setItems(ArrayList<MenuItem> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		// first check if the type of the object matches the class type
		if(!(obj instanceof CompositeProduct)) {
			return false;
		}
		CompositeProduct other = (CompositeProduct) obj;
		return this.getName().equals(other.getName());
	}
}
