package wave;

public class Angle {
	
	static final double radPerDeg=Math.PI/180;
	
	static public double r2d(double rad) {
		return rad/radPerDeg;
	}
	
	static public double d2r(double deg) {
		return radPerDeg*deg;
	}
}
