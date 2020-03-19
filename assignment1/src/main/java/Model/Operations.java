package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
public class Operations {
	

	
	public static Poly Add(Poly p1, Poly p2) {
		Poly pResult = new Poly(p1);
		for(Monom m: p2.getList()) {
			
			pResult.addMonom(m);		
			}
		return pResult;
		
		
		}
	
	
	public static Poly Subs(Poly p1, Poly p2) {
		Poly pResult = new Poly(p1);
		for(Monom m: p2.getList()) {
			pResult.subsMonom(m);		
			}
		return pResult;
		
	}
	
	
	public static void Derivate(Poly p) {
	    for(Monom el:p.getList()) {
	    	el.derivateMonom();	
	    }
	    //p.removeMonom();
	}
	
	
	public static void Integrate(Poly p) {
		for(Monom el : p.getList()) {
			el.integrateMonom();
		}
	}
	
	public static Poly Multiply(Poly p1, Poly p2) {
		Poly pResult = new Poly();
		
		
		for(Monom m1: p1.getList()) {
			for(Monom m2: p2.getList()) {
			  pResult.addMonom( m1.multiplyMonom(m2) ) ;
			}
		}
		pResult.getList().sort(new Comparator<Monom>() {

			public int compare(Monom o1, Monom o2) {
				return o2.getExp()-o1.getExp();
				
			}
		});
	
		return pResult;	
}
	
	public static ArrayList<Poly> Divide(Poly p1, Poly p2){
		Poly result =new Poly();
		
	    ArrayList<Monom> cat= new ArrayList<Monom>();
		ArrayList<Monom> p4 = new ArrayList<Monom>();
		ArrayList<Poly> p5 = new ArrayList<Poly>();
		
		System.out.println("haide");
	
		//
		while(p1.getLeadingMonom().getExp()>=p2.getLeadingMonom().getExp()) {
		Monom aux=new Monom();
		aux.setCoeff(p1.getLeadingMonom().getCoeff()/p2.getLeadingMonom().getCoeff());
		aux.setExp(p1.getLeadingMonom().getExp()-p2.getLeadingMonom().getExp());
		p4.add(new Monom(aux.getCoeff(),aux.getExp()));
		
		Poly p = new Poly(p4);
		Poly q=Multiply(p,p2);
		p1=Subs(p1,q);
		
		cat.add(aux);
		System.out.println("haide");
		
		}
		
		
		result.setPoly(cat);
		p5.add(result);
		Poly rest =new Poly(p1);
		p5.add(rest);
		return p5;
		
		
	}
	
	
}
