package bll.validators;

import dao.ProductDAO;

import model.Orders;
import model.Product;

public class QuantityValidator implements Validator<Orders> {

	
	public void validate(Orders o) {

		ProductDAO pr = new ProductDAO();
		Product p = pr.findById(o.getProductId());
		if (o.getQuantity() >=p.getQuantity()) {
			throw new IllegalArgumentException("Product out of stock");
		}

	}

}
