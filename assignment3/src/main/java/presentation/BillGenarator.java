package presentation;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.Customer;
import model.Product;
import model.Orders;

public class BillGenarator {
 private String path;
 
 public BillGenarator(String path) {
	 this.path=path;
 }
 
 public void generateBill(Product product, Customer customer, Orders order) throws IOException{
	 FileWriter write=new FileWriter(path, false);
	 PrintWriter print_line= new PrintWriter(write);
	 
	 print_line.print("--------------------------");
	 print_line.print("\n");
	 print_line.print("Customer: Mr/Mrs " + customer.getName() + " id: "+ customer.getId());
	 print_line.print("\n");
	 print_line.print("Email: " + customer.getEmail());
	 print_line.print("\n");
	 print_line.print("---------------------------");
	 print_line.print("\n");
	 print_line.print(product.getName()+ " " + " id: " + product.getId() + "-----" + order.getQuantity() + "*" + product.getPrice());
	 print_line.print("\n");
	 print_line.print("Total: " + product.getPrice()*order.getQuantity());
	 print_line.print("\n");
	 print_line.print("---------------------------");
	 write.close();
 }

}
