package view;

import physics.Point;

public class VPoint extends VBase {
	Point point;
	
	public VPoint(Point point) {
		this.point=point;
	}

	public VPoint(double x, double y) {
		this.point=new Point(x,y);
	}
	
}
