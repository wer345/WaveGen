package Physics;

public class Point {
	public double x,y;

	public Point() {
		x=0;
		y=0;
	}

	public Point(Point p) {
		x=p.x;
		y=p.y;
	}
	
	public Point(double _x,double _y) {
		x=_x;
		y=_y;
	}

	public void set(Point p) {
		x=p.x;
		y=p.y;
	}
	public void set(double _x,double _y) {
		x=_x;
		y=_y;
	}
	
	public double distance(Point p)
	{
		double dx=x-p.x;
		double dy=y-p.y;
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public Vector vectorTo(Point P2) {
		return new Vector(P2.x-x,P2.y-y);
	}
	
	public void move(Vector v) {
		x+=v.x;
		y+=v.y;
	}
	
	@Override
	public String toString() {
		//return "Point [x=" + x + ", y=" + y + "]";
		return String.format("Point(%6.2f, %6.2f)",x,y);
	}
}
