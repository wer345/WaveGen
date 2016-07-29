package view;

import java.awt.BasicStroke;
import java.awt.Color;

import physics.Line;
import physics.Point;

public class VLine extends VBase {
	Line line;
	
	public VLine(Line _line) {
		line=_line;
	}
	
	public VLine(double x1, double y1, double x2, double y2)
	{
		line= new Line(new Point(x1,y1),new Point(x2,y2));
	}

	public VLine(Point p1,Point p2,	Color color, float size)
	{
		line= new Line(p1,p2);
		this.color=color;
		this.size=size;
	}
	
	public void draw()
	{
		g.setColor(color);
		g.setStroke(new BasicStroke(nofPixel(size)));
	    g.drawLine(xPixel(line.p1.x), yPixel(line.p1.y), xPixel(line.p2.x),yPixel(line.p2.y));
	}

}
