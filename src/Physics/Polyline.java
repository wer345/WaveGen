package Physics;

import java.util.ArrayList;
import java.util.List;

public class Polyline {
	public List<Point> points= new ArrayList<Point> ();
	
	public Polyline() {
	}
	
	public void add(Point point) {
		points.add(point);
	}
	
	public void add(double x, double y) {
		points.add(new Point(x,y));
	}
}
