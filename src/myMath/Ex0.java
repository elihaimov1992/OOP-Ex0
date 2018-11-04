package myMath;

import java.util.ArrayList;

public class Ex0 {

	public static void main(String[] args) {

//		Monom m1 = new Monom (5, 3); //2x^2
//		Monom m2 = new Monom (2, 2); //5x^2
//		Monom m3 = new Monom (-2, 1); //5x^5
//
//		Polynom p1 = new Polynom();
////		p1.add(m1); p1.add(m2); p1.add(m3);
////		System.out.println(p1);
//		
		Polynom p2 = new Polynom("2x^5 + 3x^0");
		Polynom p3 = new Polynom(p2);
		System.out.println(p3);
		p2.add(new Monom("3x"));
		System.out.println(p2);
////		
//		System.out.println(p1.area(-2, 2, 0.000001));
//		System.out.println(p1);
//		
//		System.out.println(p1.root(-1, 0, 0.000001));
//		
//		Polynom pd = (Polynom) p1.derivative();
//		System.out.println(pd);
		
//		Monom ms = new Monom("x");
//		System.out.println(ms);
		
//		String dbl = "0.5";
//		double d = Double.parseDouble(dbl);
//		System.out.println(d);

		
	}
	
	

}
