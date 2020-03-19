package restaurant.model;

public class BaseProduct implements MenuItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private float price;

	public BaseProduct() {
	}

	public BaseProduct(String name, float price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float computePrice() {
		return price;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// first check if the type of the object matches the class type
		if (!(obj instanceof BaseProduct)) {
			return false;
		}
		BaseProduct other = (BaseProduct) obj;
		return this.getName().equals(other.getName());
	}

	public void add(MenuItem item) {
		// do nothing

	}

	public void remove(MenuItem item) {
		// do nothing

	}

}
