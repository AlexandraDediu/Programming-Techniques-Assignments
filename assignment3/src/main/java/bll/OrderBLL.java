package bll;

import java.util.ArrayList;
import java.util.List;

import bll.validators.QuantityValidator;
import bll.validators.Validator;
import dao.OrderDAO;
import model.Customer;
import model.Orders;

public class OrderBLL {

	private List<Validator<Orders>> odersValidators;
	private OrderDAO orderDAO;

	public OrderBLL() {
		odersValidators =new ArrayList<Validator<Orders>>();
		odersValidators.add(new QuantityValidator());
		orderDAO = new OrderDAO();
	}

	public void insert(Orders os) {
		// validate before inserting... if data is invalid , it should not be inserted
		// in the database
		for (Validator<Orders> v : odersValidators) {
			v.validate(os); 
	}

}
}