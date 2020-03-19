package MainPackage;
import View.*;

import java.util.ArrayList;

import Controller.Controller;
import Model.*;

//import java.util.Scanner;

public class Main {
//static Scanner scanner = new Scanner(System.in);
public static void main(String args[]) {

		Monom monom1 = new Monom(1, 2);
		Monom monom2 = new Monom(2, 1);
		Monom monom3 = new Monom(1, 0);
		Monom monom4 = new Monom(1, 1);
        Monom monom5 = new Monom(1,0);
////    Monom monom6=  new Monom(4,4);
////	Monom monom7=  new Monom(1,1);
////	Monom monom8= new Monom(1,2);
////	Monom monom9= new Monom(1,0);
////	System.out.println(" Monom1: " + monom1);
////	System.out.println(" Monom2: " + monom2);
////	monom1.addMonom(monom2);
//		// System.out.println(" Monom1 " + monom1);
////		
//		Poly p1= new Poly();
//		p1.addMonom(monom1);
//		p1.addMonom(monom2);	
//		p1.addMonom(monom3);
//		p1.addMonom(monom4);
//		System.out.println("p1: " + p1);
//		System.out.println(p1.getLeadingMonom());
////		
//		Poly p2= new Poly();
//		p2.addMonom(monom3);
//		p2.addMonom(monom4);
////		p2.addMonom(monom6);
//		System.out.println("p2: " + p2);
//		
//		
//		Poly p3 = new Poly();
//		p3=Operations.Add(p1, p2);
//		System.out.println("p1+p2="+p3);
//		
////		p2.addMonom(monom1);
////		System.out.println("p3: "+p3);
////
//		Poly p4 = new Poly();
//		p4=Operations.Subs(p1, p2);
//		System.out.println("p1-p2="+p4);
//    	//System.out.println(Operations.Subs(p1, p2));
//		//p2.addMonom(new Monom(-2, 2));
//		//System.out.println("p2: " + p2);
//		
//		Poly p7= new Poly();
//		p7=Operations.Multiply(p1, p2);
//		System.out.println("p1*p2=" + p7.toString());
//		
//
//		Operations.Integrate(p2);
//	    System.out.println("p2 integrated= " + p2.toString());
//		
//		Operations.Derivate(p1);
//		System.out.println("p1 derivated= " + p1.toString());
//		
//
//		Poly p5 = new Poly();
//		p5.addMonom(monom5);	
//		Operations.Derivate(p5);
//		System.out.println("p5 derivated="+p5);
//		
////		Poly p6 = new Poly();
////		p6.addMonom(monom8);
////		p6.addMonom(monom9);
////		System.out.println("p6 : "+p6);
////		
//
////		Poly p = new Poly("2x^2+1x^1");
////		System.out.println(p.toString());
        
        Poly deimpartit=new Poly();
        deimpartit.addMonom(monom1);
	    deimpartit.addMonom(monom2);	
 	    deimpartit.addMonom(monom3);
 	    System.out.println("deimpartit: " + deimpartit);
 	   
 	    Poly impartitor=new Poly();
 	    impartitor.addMonom(monom4);
 	    impartitor.addMonom(monom5);
 	    System.out.println("impartitor: " + impartitor);
// 	   
// 	    ArrayList<Poly> rezultat = new ArrayList<Poly>();
//		rezultat=Operations.Divide(deimpartit, impartitor);
//		System.out.println("rezultat: " + rezultat.get(0) + "," + rezultat.get(1));
//	
		View view = new View();
		Controller cont= new Controller(view);

	}

}
