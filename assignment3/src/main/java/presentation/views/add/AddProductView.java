package presentation.views.add;


import model.Product;
import presentation.Controller;

public class AddProductView extends AbstractAddView<Product>{

private Controller controller;
    
    public AddProductView(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void insert(Product object) {
        controller.insertProduct(object);
        
    }

}
