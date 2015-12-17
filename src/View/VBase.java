package View;
import java.awt.Color;
import java.awt.Graphics2D;

import Physics.Point;

public class VBase {
	static public Graphics2D g;
	public Point location;
	float size;
	Color color;
	
	public void setSize(float _size) {
		size=_size;
	}
	
	public void setColor(Color _color) {
		color=_color;
	}

	public int nofPixel(double d) {
		return (int)d;
	}
	public void draw()
	{
		
	}
}
