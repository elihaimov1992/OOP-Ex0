package myMath;

import java.util.ArrayList;

public class main {

	public static void main(String[] args) {

	/////////////////////// Monom Test ///////////////////////
	Monom m0 = new Monom(2,5);
	Monom m1 = new Monom(4,3);
	Monom m2 = new Monom(6,3);
	Monom m3 = new Monom(0,0);
	Monom m4 = new Monom(5,0);
	Monom m5 = new Monom(m4);
	
	System.out.println(m0.f(2)); // Should return 64
	
	System.out.println(m0.derivative()); // Should return 10x^4
	
	m1.add(m2);
	System.out.println(m1); // Should return 10x^3
	
	m1.multiply(m0);
	System.out.println(m1); // Should reutrn 10x^3 * 2x^5 = 20x^8
	
	System.out.println(m3.isZero()); // Should return true
	System.out.println(m4.isZero()); // Should return false

	System.out.println(m4.equals(m5)); // Should return true
	System.out.println(m2.equals(m5)); // Should return false
	
	System.out.println();
	/////////////////////// Polynom Test ///////////////////////
	Polynom p0 = new Polynom("0x^5 + 5x^4 + 3x^2 + 5x^2 + 2x + 4");
	Polynom p1 = new Polynom("5x^2 + 8x + 2");
	Polynom p2 = new Polynom("8x + 2");
	Polynom p3 = new Polynom("0");
	Polynom p4 = new Polynom("3x + 1");
	Polynom p5 = new Polynom(p4);
	Polynom p6 = new Polynom("-x^2 + 3");
	
	System.out.println(p0); // Should return 5x^4 + 8x^2 + 2x + 4
	
	System.out.println(p0.f(2)); // Should return 120
	
	System.out.println(p1.derivative()); // Should return 10x + 8
	
	p0.add(p1);
	System.out.println(p0); // Should return 5X^4 + 13X^2 + 10X + 6
	p0.add(new Monom("2x"));
	System.out.println(p0); // Should return 5X^4 + 13X^2 + 12X + 6
	
	p1.substract(p4);
	System.out.println(p1); // Should return 5x^2 + 5x + 1
	p1.substract(new Monom("2x"));
	System.out.println(p1); // Should return 5x^2 + 3x + 1

	
	p2.multiply(p1);
	System.out.println(p2); // Should return 40X^3 + 74X^2 + 32X + 4
	
	System.out.println(p3.isZero()); // Should return true
	System.out.println(p2.isZero()); // Should return false

	System.out.println(p4.equals(p5)); // Should return true
	System.out.println(p2.equals(p1)); // Should return false
	
	System.out.println(p6.root(-4, 0, 0.01)); // Should return -1.732
	
	System.out.println(p6.area(-2, 2, 0.0001)); // Should return 6.928

		
	}
	
	

}
