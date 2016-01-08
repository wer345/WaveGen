package Physics;

public class Line {
	public Point p1,p2;
	
	public Line(Point _P1, Point _P2) {
		p1=_P1;
		p2=_P2;
	}
	
	public Line(double x1, double y1,double x2, double y2) {
		p1=new Point(x1,y1);
		p2=new Point(x2,y2);
	}
}
