package Physics;

public class Point {
	public double x,y;
	public Point(double _x,double _y) {
		x=_x;
		y=_y;
	}
	
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
}
