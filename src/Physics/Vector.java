package Physics;

public class Vector {
	public double x,y;

	public Vector() {
		x=0;
		y=0;
	}
	
	public Vector(double _x,double _y) {
		x=_x;
		y=_y;
	}

	public void set(double _x,double _y) {
		x=_x;
		y=_y;
	}
	
	public double length()
	{
		return Math.sqrt(x*x+y*y);
	}

	public void mul(double a) {
		x*=a;
		y*=a;
	}

	public void add(Vector v)
	{
		x+=v.x;
		y+=v.y;
	}
	
	public void sub(Vector v)
	{
		x-=v.x;
		y-=v.y;
	}

	public void normalize()
	{
		mul(1/length());
	}
	
	@Override
	public String toString() {
		//return "Point [x=" + x + ", y=" + y + "]";
		return String.format("Vector(%6.2f, %6.2f)",x,y);
	}
}
