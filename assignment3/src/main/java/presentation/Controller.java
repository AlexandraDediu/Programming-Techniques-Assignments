package presentation;
import presentation.BillGenarator;

import java.io.IOException;
import java.util.List;

import bll.CustomerBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Customer;
import model.Orders;
import model.Product;

public class Controller {
	private CustomerBLL customerBLL;
	private OrderBLL orderBLL;
	private ProductBLL productBLL;

	public Controller() {
		this.customerBLL = new CustomerBLL();
		this.orderBLL = new OrderBLL();
		this.productBLL = new ProductBLL();
	}

	public void insertCustomer(Customer c) {
		customerBLL.insert(c);
	}

	public void updateCustomer(Customer c) {
		customerBLL.update(c);
	}

	public void deleteCustomer(Customer c) {
		customerBLL.delete(c);
	}

	public void insertOrder(Orders o) {
		orderBLL.insert(o);
		Product p = productBLL.findProductById(o.getProductId());
		Customer c = customerBLL.findCustomerById(o.getCustomerId());
		BillGenarator bg = new BillGenarator("order");
		try {
			bg.generateBill(p, c, o);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "order");
		try {
			pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.setQuantity(p.getQuantity()-o.getQuantity());
		productBLL.update(p);
	}

	public List<Customer> getAllCustomers() {
		return customerBLL.selectAll();
	}

	public void insertProduct(Product pr) {
        productBLL.insert(pr);
    }
	
	public void updateProduct(Product pr) {
		productBLL.update(pr);
	}
	
	public void deleteProduct(Product pr) {
		productBLL.delete(pr);
	}
	
	public List<Product> getAllProducts(){
		return productBLL.selectAll();
	}
}