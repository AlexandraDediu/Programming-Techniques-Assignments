package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.*;
import View.*;

public class Controller {
	
	View view;
	Poly p1;
	Poly p2; 
	Poly rez;
	
	public Controller (View view) {
		this.view = view;
		view.addListenerAddBtn(new AddBtn());
		view.addListenerSubstractBtn(new SubstractBtn());
		view.addListenerMultiplyBtn(new MultiplyBtn());
		view.addListenerDerivateBtn(new DerivateBtn());
		view.addListenerIntegrateBtn(new IntegrateBtn());
		view.pol1ListenerAddBtn(new Polinom1Listener());
		view.pol2ListenerAddBtn(new Polinom2Listener());
		rez = new Poly();
	}
	
	class Polinom1Listener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			try {
				p1= new Poly(view.pol1GetText());
			}catch (NumberFormatException ex) {
				view.showError("Bad input \n Ex: 2x^3+3x^1+5x^0");
			}
			
		}
		
	}
	
	class Polinom2Listener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			try {
				p2= new Poly(view.pol2GetText());
			}catch (NumberFormatException ex) {
				view.showError("Bad input \n Ex: 2x^3+3x^1+5x^0");
			}
			
		}
		
	}
	
	class AddBtn implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			try {
			p1 = new Poly(view.pol1GetText());
			p2 = new Poly(view.pol2GetText());
			
			System.out.println(p1.toString());
			rez = Operations.Add(p1, p2);
			if (rez.getList().isEmpty())
					throw new NumberFormatException();
			view.rezSetText(rez.toString());
			}catch(NumberFormatException ex) {
				view.showError("Bad Input");
			}
		}
		
	}
	
	class SubstractBtn implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			try {
			p1 = new Poly(view.pol1GetText());
			p2 = new Poly(view.pol2GetText());
			
			System.out.println(p1.toString());
			rez = Operations.Subs(p1, p2);
			if (rez.getList().isEmpty())
					throw new NumberFormatException();
			view.rezSetText(rez.toString());
			}catch(NumberFormatException ex) {
				view.showError("Bad Input");
			}
		}
		
	}
	
	class MultiplyBtn implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			try {
			p1 = new Poly(view.pol1GetText());
			p2 = new Poly(view.pol2GetText());
			
			System.out.println(p1.toString());
			rez = Operations.Multiply(p1, p2);
			if (rez.getList().isEmpty())
					throw new NumberFormatException();
			view.rezSetText(rez.toString());
			}catch(NumberFormatException ex) {
				view.showError("Bad Input");
			}
		}
		
	}
	
	class DerivateBtn implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
			p1 = new Poly(view.pol1GetText());
			System.out.println(p1.toString());
			Operations.Derivate(p1);
			if (p1.getList().isEmpty())
				throw new NumberFormatException();
		    view.rezSetText(p1.toString());
		    }catch(NumberFormatException ex) {
			view.showError("Bad Input");
		}
	}

}
	
	class IntegrateBtn implements  ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
			p1 = new Poly(view.pol1GetText());
			System.out.println(p1.toString());
			Operations.Integrate(p1);
			if (p1.getList().isEmpty())
				throw new NumberFormatException();
		    view.rezSetText(p1.toString());
		    }catch(NumberFormatException ex) {
			view.showError("Bad Input");
		}
	}

}
	
}