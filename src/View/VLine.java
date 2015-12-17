package View;

import java.awt.BasicStroke;
import java.awt.Color;

import Physics.Line;
import Physics.Point;

public class VLine extends VBase {
	Line line;
	
	public VLine(Line _line) {
		line=_line;
	}
	
	public void draw()
	{
		g.setColor(color);
		g.setStroke(new BasicStroke(nofPixel(size)));
	    g.drawLine(xPixel(line.p1.x), yPixel(line.p1.y), xPixel(line.p2.x),yPixel(line.p2.y));
	}

}
