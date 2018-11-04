
package myMath;
/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 * @author Yosef Golubchik (209195353), Eli Haimov (308019306), Elad Cohen (307993030)
 */
public class Monom implements function{

	/**
	 * Default monom constructor
	 * default coefficient and power values are zero.
	 */
	public Monom(){
		this.set_coefficient(0);
		this.set_power(0);
	}
	/**
	 * constructor that creates a monom from given values
	 * @param a coefficient
	 * @param b power
	 */
	public Monom(double a, int b){
		if (b < 0) {
			throw new RuntimeException("Power has to be non-negative");
		}
		this.set_coefficient(a);
		this.set_power(b);
	}
	/**
	 * copy constructor
	 * @param ot monom to be copied
	 */
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	/**
	 * creates a monom from a string
	 * @param s string to be converted
	 */
	public Monom(String s) {
		Monom ms = init_from_string(s);
		this.set_coefficient(ms.get_coefficient());
		this.set_power(ms.get_power());
	}
	// ***************** add your code below **********************
	public double get_coefficient(){
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/**
	 * gives value of monom for a certain x value
	 * ex: monom: 5x^2, f(3) = 5*3^2 = 45
	 * @param x value to be inputed into monom
	 */
	public double f(double x) {
		return this.get_coefficient() * Math.pow(x, this.get_power());
	}
	/**
	 * returns a monom that is the derivative of the given monom
	 * ex: monom: 5x^2, derivative: 10x
	 */
	public Monom derivative() {
		double a = 0;
		int b = 0;

		if (isZero() && this.get_power() != 0) {
			b = this.get_power() - 1;
		}
		else if (!isZero() && this.get_power() != 0) {
			a = this.get_coefficient()*this.get_power();
			b = this.get_power()-1;
		}
		return new Monom(a,b);
	}
	/**
	 * adds monom m to the given monom
	 * @param m the monom to be added
	 */
	public void add(Monom m) {

		if(m.get_power()==this.get_power()) {
			this.set_coefficient(this.get_coefficient()+m.get_coefficient());
		}
	}
	/**
	 * multiples given monom by monom x
	 * @param x monom for given monom to be multiplied by
	 */
	public void multiply(Monom x) {

		this.set_coefficient(this.get_coefficient()*x.get_coefficient());
		this.set_power(this.get_power()+x.get_power());
	}
	/**
	 * converts monom to string
	 */
	public String toString() {
		if (this.get_power() == 0) return "" + this.get_coefficient();
		else if (this.get_power() == 1) return "" + this.get_coefficient() + "X";
		else return this.get_coefficient() + "X^"+ this.get_power();
	}
	/**
	 * checks if a monom is a zero monom (monom whose coefficient equals zero)
	 */
	public boolean isZero() {
		return this.get_coefficient()==0;
	}
	/**
	 * checks if two monoms are equal.
	 * monoms are equal if their coefficients and powers are the same
	 * @param m2 monom to be compared to
	 */
	public boolean equals(Monom m2) {
		return this.get_coefficient() == m2.get_coefficient() && this.get_power() == m2.get_power();
	}





	//****************** Private Methods and Data *****************

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		this._power = p;
	}

	

	private static Monom init_from_string(String s){
		if(s==null){throw new RuntimeException("ERR -> String is null");}
		double a=0;
		int b=0;
		String in=s.toLowerCase();
		int ind_x= in.indexOf("x");
		if(ind_x<0){
			try{
				a=Double.parseDouble(in);
			}
			catch(Exception e){
				throw new RuntimeException("Invalid String");
			}
		}
		else{
			String c= in.substring(0,ind_x);
			int ind_p=in.indexOf("^");
			if(ind_p<0){
				if (ind_x != s.length()-1) {
					throw new RuntimeException("Invalid String");
				}
			b=1;
			if (in.substring(0,ind_x).equals("")) {
				a = 1;
			}
			else a = Double.parseDouble(in.substring(0,ind_x));
			}
			else{
				try{
					b=Integer.parseInt(in.substring(ind_p+1));
					a = Double.parseDouble(in.substring(0,ind_x));
				}
				catch(Exception e){
					throw new RuntimeException("Invalid String");
				}
			}
		}
		return new Monom(a,b);
	}
	

	private double _coefficient; // 
	private int _power; 
}
