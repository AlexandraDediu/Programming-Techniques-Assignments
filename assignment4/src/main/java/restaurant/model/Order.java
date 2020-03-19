package restaurant.model;

import java.util.Date;

public class Order {
	private int orderId;
	private Date orderDate;
	private int table;
	

	public Order(int orderId, Date orderDate, int table) {
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.table = table;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getTable() {
		return table;
	}

	public void setTable(int table) {
		this.table = table;
	}

	@Override
	public int hashCode() {
		// use hashCode() of String class
		String str = orderId + "_" + orderDate.toString() + "_" + table;
		return str.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Order)) {
			return false;
		}
		return this.orderId == ((Order) obj).getOrderId();
	}
}
