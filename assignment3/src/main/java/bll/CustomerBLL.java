package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.CustomerAgeValidator;
import bll.validators.Validator;
import dao.CustomerDAO;
import model.Customer;

public class CustomerBLL {
    private CustomerDAO customerDAO;

    private List<Validator<Customer>> validators;

    public CustomerBLL() {
        customerDAO = new CustomerDAO();
        validators = new ArrayList<Validator<Customer>>();
        validators.add(new EmailValidator());
        validators.add(new CustomerAgeValidator());
    }

    public Customer findCustomerById(int id) {
        Customer ct = customerDAO.findById(id);
        if (ct == null) {
            throw new NoSuchElementException("The customer with id =" + id + " was not found!");
        }
        return ct;
    }

    public void insert(Customer c) {        
        //validate before inserting..before if data is invalid should not be inserted in the database
        for (Validator<Customer> v : validators) {
            v.validate(c);
        }
        customerDAO.insert(c);
    }

    public void update(Customer c) {
        for (Validator<Customer> v : validators) {
            v.validate(c);
        }
        customerDAO.update(c);
    }

    public void delete(Customer c) {
       customerDAO.delete(c);
    }

    public List<Customer> selectAll() {
        List<Customer> ct = new ArrayList<Customer>();
        ct = customerDAO.selectAll();
        if (ct == null) {
            throw new NoSuchElementException("Elements were not found!");
        }
        return ct;
    }

}