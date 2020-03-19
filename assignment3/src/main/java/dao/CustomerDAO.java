package dao;

import java.util.ArrayList;
import java.util.List;
import model.Customer;

public class CustomerDAO extends AbstractDAO<Customer> {
	
	//findById
	public  Customer findById(int id) {
		return super.findById(id,"id");
	}
	
	//insert
	public void insert(Customer c){
		super.insert(c);
	}
	
	public void update(Customer c){
		super.update(c);
	}
	
	public void delete(Customer c){
		super.delete(c);			
	}
	
	public List<Customer> selectAll(){
		return super.selectAll();
	}
	
}