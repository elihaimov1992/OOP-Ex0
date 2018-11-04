package myMath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

import javax.management.RuntimeErrorException;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 * @author Yosef Golubchik (209195353), Eli Haimov (308019306), Elad Cohen (307993030)
 *
 */
public class Polynom implements Polynom_able{

	// ********** add your code below ***********
	/**
	 * An arraylist that contains the monoms that make up the polynom.
	 */
	ArrayList<Monom> list = new ArrayList<>();
	/**
	 * default constructor
	 */
	public Polynom(){
		
	}
	/**
	 * constructs a polynom from a string
	 * @param s
	 */
	public Polynom(String s){
		this();
		Polynom p = init_from_string(s);
		Iterator<Monom> it= p.iteretor();
		while(it.hasNext()){
			Monom m = it.next();
			this.add(m);
		}
	}
	/**
	 * copy constructor
	 * @param ot polynom to be copied
	 */
	public Polynom(Polynom ot) {
		this();
		Iterator<Monom> it= ot.iteretor();
		while(it.hasNext()){
			Monom m = it.next();
			this.add(m);
		}
	}
	/**
	 * gives value of polynom for a certain x value
	 * ex: polynom: 5x^2 + 3x + 2, f(3) = 5*3^2 + 3*3 + 2 = 56
	 * @param x value to be inputed into polynom
	 * @return The value of the polynom at x.
	 */
	@Override
	public double f(double x) {
		double sum = 0;
		for (int i = 0; i < this.list.size(); i++) {
			sum += this.list.get(i).f(x);
		}
		return sum;
	}

	/**
	 * adds another polynom p1 to given polynom.
	 * @param p1 polynom to be added.
	 */
	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> it = p1.iteretor();
		while(it.hasNext()) {
			Monom x = it.next();
			this.add(x);
		}
	}

	/**
	 * Adds to the current polynom the given monom m1.
	 * @param m1 Monom to be added.
	 */
	@Override
	public void add(Monom m1) {
		boolean added = false;
		Monom_Comperator cmp = new Monom_Comperator();
		for (int i = 0; i < this.list.size(); i++) {
			if (this.list.get(i).get_power()==m1.get_power()) {
				this.list.get(i).add(m1);
				added = true;
			}
		}
		if(!added) {
			list.add(m1);
			list.sort(cmp);
		}
		
	}
	/**
	 * Subtracts from the current polynom the given polynom p1.
	 * @param p1 Polynom to be subtracted.
	 */
	@Override
	public void substract(Polynom_able p1) {
		Polynom temp = new Polynom();
		Iterator<Monom> it = p1.iteretor();
		while(it.hasNext()) {
			Monom x = it.next();
			Monom mNew = new Monom(-1 * x.get_coefficient(), x.get_power());
			temp.add(mNew);
		}
		this.add(temp);
		it = p1.iteretor();
		while(it.hasNext()) {
			Monom x = it.next();
			if (x.isZero()) this.list.remove(x);
		}
	}
	/**
	 * Subtracts from the current polynom the given monom m1.
	 * @param m1 Monom to be subtracted.
	 */
	public void substract(Monom m1) {
		Polynom temp = new Polynom();
		Monom mNew = new Monom(-1 * m1.get_coefficient(), m1.get_power());
		temp.add(mNew);
		this.add(temp);
		Iterator<Monom> it = this.iteretor();
		while(it.hasNext()) {
			Monom x = it.next();
			if (x.isZero()) this.list.remove(x);
		}
	}
	/**
	 * Multiplies the current polynom by a given polynom p1.
	 * @param p1 Polynom to be multiplied by.
	 */
	@Override
	public void multiply(Polynom_able p1) {
		Polynom sum = new Polynom();
		Iterator<Monom> it1 = this.iteretor();
		Iterator<Monom> it2 = p1.iteretor();
		while(it1.hasNext()) {
			Monom m1 = it1.next();
			while(it2.hasNext()) {
				Monom m2 = it2.next();
				Monom m3 = new Monom(m1.get_coefficient(),m1.get_power());
				m3.multiply(m2);
				sum.add(m3);
			}
			it2 = p1.iteretor();
		}
		Polynom copy = new Polynom();
		copy.add(this);
		
		this.substract(copy);
		this.add(sum);
		
	}

	/**
	 * Checks whether the current polynom is equal to a given polynom p1.
	 * Two polynoms are considered equal if their coefficients and powers are equal.
	 * @param p1 Polynom to be compared to.
	 * @return True if the polynoms are equal, false otherwise.
	 */
	@Override
	public boolean equals(Polynom_able p1) {
		Iterator<Monom> it1 = this.iteretor();
		Iterator<Monom> it2 = p1.iteretor();
		while(it1.hasNext() || it2.hasNext()) {
			if (it1.hasNext() && !it2.hasNext() || !it1.hasNext() && it2.hasNext())
				return false;
			Monom m1 = it1.next();
			Monom m2 = it2.next();
			if (!(m1.equals(m2))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether the current polynom's coefficient is zero.
	 * @return True if the polynom is a zero polynom, false otherwise.
	 */
	@Override
	public boolean isZero() {
		return this.list.size() == 0;
	}

	/**
	 * This function uses the Bisection Method to find a root of the polynom between x0 and x1.
	 * https://en.wikipedia.org/wiki/Bisection_method
	 * @param x0 The left boundary of the range.
	 * @param x1 The right boundary of the range.
	 * @param eps The maximum distance between x0 and x1 we will allow. The function will stop when x0 and x1 get at least this close.
	 * @return The x value of the root.
	 */
	@Override
	public double root(double x0, double x1, double eps) {
		
		double y0 = f(x0);
		double y1 = f(x1);

		if(y0 * y1 > 0)
			System.err.println("f(x0) and f(x1) should have different signs");
		
		double x_diff = Math.abs(x1- x0);
		double y_diff = Math.abs(f(x0) - f(x1));
		
		if (x_diff > eps || y_diff > eps) {
			double x_mid = (x0 + x1) / 2;
			double y_mid = f(x_mid);
			
			if(y0 * y_mid <= 0)
				return root(x0, x_mid, eps);
			else
				return root(x_mid, x1, eps);
		}
		
		return x0;
	}

	/**
	 * Creates a copy of the current polynom.
	 * @return A copy of the polynom.
	 */
	@Override
	public Polynom_able copy() {
		Polynom copy = new Polynom();

		Iterator<Monom> it = this.iteretor();
		while(it.hasNext()) {
			Monom x = it.next();
			Monom mNew = new Monom(x.get_coefficient(), x.get_power());
			copy.add(mNew);
		}
		return copy;
	}

	/**
	 * Creates a new polynom which is the derivative of the current polynom.
	 * @return The derived polynom.
	 */
	@Override
	public Polynom_able derivative() {
		Polynom der = new Polynom();
		Iterator<Monom> it = this.iteretor();
		while (it.hasNext()) {
			Monom curr = it.next(); // Current Monom
			Monom cder = curr.derivative(); // Derivative of current Monom
			der.add(cder);
		}
		return der;
	}

	/**
	 * Computes the Riemann sum of the current polynom between x0 and x1.
	 * https://en.wikipedia.org/wiki/Riemann_sum
	 * @param x0 The left boundary of the range.
	 * @param x1 The right boundary of the range.
	 * @param eps The width of one rectangle.
	 * @return The computed area value.
	 */
	@Override
	public double area(double x0, double x1, double eps) {
		if (x1 < x0) {
			throw new RuntimeException("X0 must be smaller than X1");
		}
		double sum=0;
		for (double i = x0; i <= x1; i=i+eps) {
			if (f(i) > 0 && f(i+eps) > 0) {
				sum += eps * Math.max(f(i), f(i+eps));
			}
		}
		return sum;
	}

	/**
	 * @return An iterator to the arraylist the contains the monoms that make up the polynom.
	 */
	@Override
	public Iterator<Monom> iteretor() {
		return this.list.iterator();
	}

	/**
	 * Converts the current polynom to a string.
	 * @return A string version of the current polynom.
	 */
	public String toString() {
		String ans;
		if (isZero()) {
			ans = "0";
		}
		else {
			ans = list.get(0).toString();
			for (int i = 1; i < list.size(); i++) {
				ans = ans + " + " + list.get(i).toString();
			}
		}
		return ans;
	}
	
	/**
	 * A helper function for the constructor that gets a string.
	 * @param s String to be converted into a polynom.
	 * @return A polynom constructed from the string.
	 */
	private Polynom init_from_string(String s) {
		if (s == null) {
			throw new RuntimeException("String is empty");
		}
		String[] string_monoms = s.split(" ");
		Polynom pnew = new Polynom();
		for (int i = 0; i < string_monoms.length; i++) {
			String s_monom = string_monoms[i];
			if (!s_monom.equals("+") ){
				Monom m = new Monom(s_monom);
				if (m.get_coefficient() != 0)
					pnew.add(m);
			}
		}
		return pnew;
	}
}
