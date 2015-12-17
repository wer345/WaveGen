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
	    g.drawLine((int)line.p1.x, (int)line.p1.y, (int)line.p2.x,(int) line.p2.y);
	}

}
