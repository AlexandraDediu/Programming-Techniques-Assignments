package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

public class ProductBLL {

	private ProductDAO productDAO;
	
	public ProductBLL() {
		productDAO=new ProductDAO();
		
	}

	public Product findProductById(int id) {
		Product pr = productDAO.findById(id);
		if (pr == null) {
			throw new NoSuchElementException("The product with id =" + id + " was not found!");
		}
		return pr;
	}
	
	public void insert(Product p) {
        productDAO.insert(p);
    }

	public void delete(Product p) {
		productDAO.delete(p);
		
	}
	
	public void update(Product p) {
		productDAO.update(p);
	}

	public List<Product> selectAll() {
		List<Product> pr = new ArrayList<Product>();
		pr = productDAO.selectAll();
		if (pr == null) {
			throw new NoSuchElementException(" found!");
		}
		return pr;
	}

}
