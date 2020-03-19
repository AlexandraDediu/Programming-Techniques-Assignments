/**
 * 
 */
package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import Model.Monom;
import Model.Operations;
import Model.Poly;

/**
 * @author alexandra
 *
 */
public class Testare {
	@Test
	public void testAdd() {
		Poly poly1= new Poly("3x^1");
		Poly poly2 = new Poly("5x^2+5x^1");

		Assert.assertTrue(Operations.Add(poly1, poly2).toString().equals("5.0x^2+8.0x^1"));
	}
	
	@Test
	public void testSub() {
		Poly poly1= new Poly("3x^1");
		Poly poly2 = new Poly("5x^2+5x^1");
		Assert.assertTrue(Operations.Subs(poly1, poly2).toString().equals("-5.0x^2-2.0x^1"));
		
	}
	
	@Test
	public void testMultiply() {
	Poly poly1= new Poly("3x^1");
	Poly poly2 = new Poly("5x^2+5x^1");
	Assert.assertTrue(Operations.Multiply(poly1, poly2).toString().equals("15.0x^3+15.0x^2"));

}
	@Test
	public void testDerivate() {
		Poly p1= new Poly("5x^2+5x^1");
		Operations.Derivate(p1);
		Assert.assertTrue(p1.toString().equals("10.0x^1+5.0x^0"));
	}
	
	@Test
	public void testIntegrate() {
		Poly p1= new Poly("5x^2+5x^1");
		Operations.Integrate(p1);
		Assert.assertTrue(p1.toString().equals("1.6666666666666667x^3+2.5x^2"));
	}
	
	

}