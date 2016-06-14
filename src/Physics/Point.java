package Physics;

public class Point {
	public double x,y;

	public Point() {
		x=0;
		y=0;
	}
	
	public Point(double _x,double _y) {
		x=_x;
		y=_y;
	}

	public void set(double _x,double _y) {
		x=_x;
		y=_y;
	}
	
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
}
