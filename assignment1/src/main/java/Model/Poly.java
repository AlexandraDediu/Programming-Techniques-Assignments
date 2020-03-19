package Model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Comparator;
import java.util.Iterator;

public class Poly {

	public ArrayList<Monom> list;

	public Poly() {
		
		list = new ArrayList<Monom>();
	}
	
	public Poly( ArrayList<Monom> Alist) {
		this.list=Alist;
	}
	
	public Poly(String input) {
		list = new ArrayList<Monom>();
		//Pattern p=Pattern.compile("([+-]?[^-+]+)");
		Pattern p = Pattern.compile("(-?\\b\\d+)[xX]\\^(-?\\d+\\b)");
		Matcher m = p.matcher(input);
		while (m.find()) {
			//System.out.println("Coef: " + m.group(1));
			float coef = Float.parseFloat(m.group(1));
			
			//System.out.println("Degree: " + m.group(2));
			int deg = Integer.parseInt(m.group(2));
			Monom temp = new Monom(coef, deg);
			list.add(temp);
		}
		
	}
	
	public Poly(Poly copy) {
		this();
		
		for(Monom el:copy.list)
		{
			this.addMonom(el);
	
		}
		
	}

	public void addMonom(Monom m) {
		
		boolean entered=false;
		for(Monom el:list) {
			
			if(el.getExp()==m.getExp()) {
				el.addMonom(m);
				if(el.getCoeff()==0||(el.getCoeff()==0&& el.getExp()==0)) list.remove(el);
				entered=true;
				break;
			}
		}
		if(entered ==false) {
			this.list.add(new Monom(m.getCoeff(), m.getExp()));
		}
		list.sort(new Comparator<Monom>() {

			public int compare(Monom o1, Monom o2) {
				return o2.getExp()-o1.getExp();
				
			}
		});
	}
	
	public void subsMonom(Monom m) {
		boolean entered=false;
		for(Monom el:list) {
			
			if(el.getExp()==m.getExp()) {
				el.subsMonom(m);
				entered=true;
			}	
		}
		if(entered ==false) {
			this.list.add(new Monom(m.getCoeff()*(-1), m.getExp()));
		}
		
		list.sort(new Comparator<Monom>() {

			public int compare(Monom o1, Monom o2) {
				return o2.getExp()-o1.getExp();
				
			}
		});
	}
	
	
	public ArrayList<Monom> getList(){
		return list;
	}

	public ArrayList<Monom> setList(){
		return list;
	}
	
	public void setPoly(ArrayList<Monom> Alist) {
		this.list=Alist;
	}

	
	public String toString() {
		String s = "";
		int count = 0;
		for (Monom el : list) {
			if(count!=0){
				if(el.getCoeff()>=0){
						s+="+";
						}
				}
			else {
				count++;
			     }
			s += el.toString();
			
	}
		return s;
   }
	

	
	public Monom getLeadingMonom() {
		Monom leadingMonom=new Monom();
		leadingMonom.setExp(-1);
		for(Monom el:list) {
			if(el.getExp()>leadingMonom.getExp()) {
				leadingMonom.setCoeff(el.getCoeff());
				leadingMonom.setExp(el.getExp());
			}
			
		}
		return leadingMonom;
	}
		

}
	
