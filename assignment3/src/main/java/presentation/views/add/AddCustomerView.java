package presentation.views.add;

import model.Customer;
import presentation.Controller;

public class AddCustomerView extends AbstractAddView<Customer> {
    private Controller controller;
    
    public AddCustomerView(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void insert(Customer object) {
        controller.insertCustomer(object);
        
    }
    
    
}
