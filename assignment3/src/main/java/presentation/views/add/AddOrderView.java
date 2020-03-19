package presentation.views.add;

import javax.swing.JOptionPane;

import model.Orders;
import presentation.Controller;

public class AddOrderView extends AbstractAddView<Orders>{

	 private Controller controller;
	 public AddOrderView(Controller controller) {
	        this.controller = controller;
	    }
    @Override
    public void insert(Orders object) {
    	try {
    	controller.insertOrder(object);
        
    }catch(IllegalArgumentException e) {
    	JOptionPane.showMessageDialog(addView, e.getMessage());
    }
    

}
}