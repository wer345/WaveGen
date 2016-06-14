
/**
	Code modified from https://www.math.ksu.edu/~bennett/jomacg/c.html 
*/
public class Complex extends Object {

static double errorAllowance=1e-13;	
private double x,y;

/**
    Constructs the complex number z = u + i*v
    @param u Real part
    @param v Imaginary part
*/
public Complex(double u,double v) {
    x=u;
    y=v;
}

/**
    Real part of this Complex number 
    (the x-coordinate in rectangular coordinates).
    @return Re[z] where z is this Complex number.
*/
public double real() {
    return x;
}

/**
    Imaginary part of this Complex number 
    (the y-coordinate in rectangular coordinates).
    @return Im[z] where z is this Complex number.
*/
public double imag() {
    return y;
}

/**
    Modulus of this Complex number
    (the distance from the origin in polar coordinates).
    @return |z| where z is this Complex number.
*/
public double mod() {
    if (x!=0 || y!=0) {
        return Math.sqrt(x*x+y*y);
    } else {
        return 0d;
    }
}

/**
	check if the two number are equal
	@return true if they are equal.
*/
public boolean equals(Complex x) {
	double error=minus(x).mod();
	if (error<errorAllowance) 
		return true;
	else 
    return false;
}

/**
    Argument of this Complex number 
    (the angle in radians with the x-axis in polar coordinates).
    @return arg(z) where z is this Complex number.
*/
public double arg() {
    double a=Math.atan2(y,x);
    if(a<0)
    	a=2*Math.PI+a;
    return a;
}

/**
    Complex conjugate of this Complex number
    (the conjugate of x+i*y is x-i*y).
    @return z-bar where z is this Complex number.
*/
public Complex conj() {
    return new Complex(x,-y);
}

/**
    Addition of Complex numbers (doesn't change this Complex number).
    <br>(x+i*y) + (s+i*t) = (x+s)+i*(y+t).
    @param w is the number to add.
    @return z+w where z is this Complex number.
*/
public Complex plus(Complex w) {
    return new Complex(x+w.real(),y+w.imag());
}

/**
    Subtraction of Complex numbers (doesn't change this Complex number).
    <br>(x+i*y) - (s+i*t) = (x-s)+i*(y-t).
    @param w is the number to subtract.
    @return z-w where z is this Complex number.
*/
public Complex minus(Complex w) {
    return new Complex(x-w.real(),y-w.imag());
}

/**
    Complex multiplication (doesn't change this Complex number).
    @param w is the number to multiply by.
    @return z*w where z is this Complex number.
*/
public Complex times(Complex w) {
    return new Complex(x*w.real()-y*w.imag(),x*w.imag()+y*w.real());
}

/**
    Division of Complex numbers (doesn't change this Complex number).
    <br>(x+i*y)/(s+i*t) = ((x*s+y*t) + i*(y*s-y*t)) / (s^2+t^2)
    @param w is the number to divide by
    @return new Complex number z/w where z is this Complex number  
*/
public Complex div(Complex w) {
    double den=Math.pow(w.mod(),2);
    return new Complex((x*w.real()+y*w.imag())/den,(y*w.real()-x*w.imag())/den);
}

/**
    Complex exponential (doesn't change this Complex number).
    @return exp(z) where z is this Complex number.
*/
public Complex exp() {
    return new Complex(Math.exp(x)*Math.cos(y),Math.exp(x)*Math.sin(y));
}

/**
    Principal branch of the Complex logarithm of this Complex number.
    (doesn't change this Complex number).
    The principal branch is the branch with -pi < arg <= pi.
    @return log(z) where z is this Complex number.
*/
public Complex log() {
    return new Complex(Math.log(this.mod()),this.arg());
}

/**
    Complex square root (doesn't change this complex number).
    Computes the principal branch of the square root, which 
    is the value with 0 <= arg < pi.
    @return sqrt(z) where z is this Complex number.
*/
public Complex[] sqrt() {
	Complex [] rst= new Complex[2];
    double r=Math.sqrt(this.mod());
	double step=Math.PI;
    double theta=this.arg()/2;
	for(int i=0;i<2;i++) {
		rst[i]= new Complex(r*Math.cos(theta),r*Math.sin(theta));
		theta+=step;
	}
	return rst;
}

/**
Complex cubic root (doesn't change this complex number).
Computes the principal branch of the cubic root, which 
is the value with 0 <= arg < pi.
@return sqrt(z) where z is this Complex number.
*/
public Complex[] cubrt() {
	Complex [] rst= new Complex[3];
	double r=Math.cbrt(this.mod());
	double step=2*Math.PI/3;
	double theta=this.arg()/3;
	for(int i=0;i<3;i++) {
		rst[i]=new Complex(r*Math.cos(theta),r*Math.sin(theta));
		theta+=step;
	}
	return rst;
}

// Real cosh function (used to compute complex trig functions)
private double cosh(double theta) {
    return (Math.exp(theta)+Math.exp(-theta))/2;
}

// Real sinh function (used to compute complex trig functions)
private double sinh(double theta) {
    return (Math.exp(theta)-Math.exp(-theta))/2;
}

/**
    Sine of this Complex number (doesn't change this Complex number).
    <br>sin(z) = (exp(i*z)-exp(-i*z))/(2*i).
    @return sin(z) where z is this Complex number.
*/
public Complex sin() {
    return new Complex(cosh(y)*Math.sin(x),sinh(y)*Math.cos(x));
}

/**
    Cosine of this Complex number (doesn't change this Complex number).
    <br>cos(z) = (exp(i*z)+exp(-i*z))/ 2.
    @return cos(z) where z is this Complex number.
*/
public Complex cos() {
    return new Complex(cosh(y)*Math.cos(x),-sinh(y)*Math.sin(x));
}

/**
    Hyperbolic sine of this Complex number 
    (doesn't change this Complex number).
    <br>sinh(z) = (exp(z)-exp(-z))/2.
    @return sinh(z) where z is this Complex number.
*/
public Complex sinh() {
    return new Complex(sinh(x)*Math.cos(y),cosh(x)*Math.sin(y));
}

/**
    Hyperbolic cosine of this Complex number 
    (doesn't change this Complex number).
    <br>cosh(z) = (exp(z) + exp(-z)) / 2.
    @return cosh(z) where z is this Complex number.
*/
public Complex cosh() {
    return new Complex(cosh(x)*Math.cos(y),sinh(x)*Math.sin(y));
}

/**
    Tangent of this Complex number (doesn't change this Complex number).
    <br>tan(z) = sin(z)/cos(z).
    @return tan(z) where z is this Complex number.
*/
public Complex tan() {
    return (this.sin()).div(this.cos());
}

/**
    Negative of this complex number (chs stands for change sign). 
    This produces a new Complex number and doesn't change 
    this Complex number.
    <br>-(x+i*y) = -x-i*y.
    @return -z where z is this Complex number.
*/
public Complex chs() {
    return new Complex(-x,-y);
}

/**
    String representation of this Complex number.
    @return x+i*y, x-i*y, x, or i*y as appropriate.
*/
public String toString() {
	return toString("");
}

public String toString(String fmt) {
	String s="+";
	if(y<0)
		s="-";
	String format="%"+fmt+"f %s %"+fmt+"fi";
	//format="%f %s %fi";
	String text= String.format(format, x,s,Math.abs(y));
	return text;
	
//	if (x!=0 && y>0) {
//	    return x+" + "+y+"i";
//	}
//	if (x!=0 && y<0) {
//	    return x+" - "+(-y)+"i";
//	}
//	if (y==0) {
//	    return String.valueOf(x);
//	}
//	if (x==0) {
//	    return y+"i";
//	}
//	// shouldn't get here (unless Inf or NaN)
//	return x+" + i*"+y;
    
}       

}
