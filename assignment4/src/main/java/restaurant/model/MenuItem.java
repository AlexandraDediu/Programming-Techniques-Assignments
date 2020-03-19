package restaurant.model;

import java.io.Serializable;

public interface MenuItem extends Serializable {
	float computePrice();
	void add(MenuItem item);
	void remove(MenuItem item);
	String getName();

}
