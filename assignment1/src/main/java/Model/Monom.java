package Model;



public class Monom {
	private double coeff;
	private int exp;

	public Monom() {
		this(0, 0);
	}

	public Monom(double aCoeff, int anExp) {
		coeff = aCoeff;
		exp = anExp;
	}

	public void setCoeff(double coeff) {
		this.coeff = coeff;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public double getCoeff() {
		return coeff;
	}

	public int getExp() {
		return exp;
	}
	

	public void addMonom(Monom m) {

		if (m.exp == this.exp) {
			this.coeff += m.coeff;
		}
	}
	

	public void subsMonom(Monom m) {
		if (m.exp == this.exp) {
			this.coeff = this.coeff- m.coeff;
		}
		if ((m.exp == this.exp)&&(m.coeff==this.coeff)) {
			this.coeff = this.coeff- m.coeff;
			this.exp=this.exp-m.exp;
		}
		
	}
	
	public void derivateMonom() {
		if(this.exp==0) {
			this.coeff=this.coeff*this.exp;
			this.setExp(0);
			
		}
		else {
		this.coeff=this.coeff * this.exp;
		this.exp--;
		}
	}

	public void integrateMonom() {
		this.exp=this.exp+1;
		this.coeff /= this.exp;
	}
	
	public Monom multiplyMonom(Monom m) {
		
		return new Monom(this.coeff*m.coeff , this.exp+m.exp);	
	}
	
	@Override
	public String toString() {
		return (coeff + "x^" + exp);
	}
}