package view;

import java.awt.BasicStroke;
import java.awt.Color;

import Physics.Polyline;
import Physics.Point;

public class VPolyline extends VBase {
	Polyline polyline;
	
	public VPolyline(Polyline _polyline) {
		polyline=_polyline;
	}
	
	
	public void draw()
	{
		g.setColor(color);
		g.setStroke(new BasicStroke(nofPixel(size)));
		Point p0=null;
		boolean first=true;
		for(Point point:polyline.points) {
			if(!first)
				g.drawLine(xPixel(p0.x), yPixel(p0.y), xPixel(point.x),yPixel(point.y));
			else
				first=false;
			p0=point;
		}
	}

}
