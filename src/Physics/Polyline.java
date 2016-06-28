package Physics;

import java.util.ArrayList;
import java.util.List;

public class Polyline {
	public List<Point> points= new ArrayList<Point> ();
	
	public Polyline() {
	}

	public Polyline(int nofPoints) {
		for(int i=0;i<nofPoints;i++)
			add(0,0);
	}
	
	public void add(Point point) {
		points.add(point);
	}
	
	public void add(double x, double y) {
		points.add(new Point(x,y));
	}

	
	public void set(int idx,double x, double y) {
		Point p=points.get(idx);
		p.set(x, y);
	}
	
}
