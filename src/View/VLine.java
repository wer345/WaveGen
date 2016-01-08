package View;

import java.awt.BasicStroke;
import java.awt.Color;

import Physics.Line;
import Physics.Point;

public class VLine extends VBase {
	Line polyline;
	
	public VLine(Line _line) {
		polyline=_line;
	}
	
	public VLine(double x1, double y1, double x2, double y2)
	{
		polyline= new Line(new Point(x1,y1),new Point(x2,y2));
	}
	
	public void draw()
	{
		g.setColor(color);
		g.setStroke(new BasicStroke(nofPixel(size)));
	    g.drawLine(xPixel(polyline.p1.x), yPixel(polyline.p1.y), xPixel(polyline.p2.x),yPixel(polyline.p2.y));
	}

}
