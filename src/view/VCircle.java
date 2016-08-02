package view;

import java.awt.BasicStroke;
import java.awt.Color;

import physics.Line;
import physics.Point;

public class VCircle extends VBase {
	Point location;
	
	public VCircle(Point _location) {
		location=_location;
	}

	public VCircle(Point _location, Color color, float size)
	{
		location=_location;
		this.color=color;
		this.size=size;
	}
	

	public VCircle(double x, double y, Color color, float size)
	{
		location= new Point(x,y);
		this.color=color;
		this.size=size;
	}
	
	public void draw()
	{
//		g.setColor(color);
//		g.setStroke(new BasicStroke(nofPixel(size)));
        g.setPaint(color);
        g.fillOval(xPixel(location.x)-(int)(size/2), yPixel(location.y)-(int)(size/2), (int)size, (int)size);
	}

}
