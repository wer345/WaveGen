package view;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import physics.Line;
import physics.Point;

public class VBase {
	static public Graphics2D g;
	static int windowHeight=400;
	static int windowWidth=600;
	static public Range rangeDefault=new Range(0,0,windowWidth,windowHeight); // the points of left bottom, and right upper
	
	//public Point location;
	float size;
	Color color;
	Range range;
	double rx;
	double ry;
	Move moveTo=null;			// move to Parents
	Move moveToAbsolute=null;	// move to the screen
	
	public List <VBase> children=null;
	
	public VBase() {
		setValueRange(rangeDefault);
	}
	
	public void setValueRange(Range range) {
		this.range = range;
		rx=windowWidth/(range.x2-range.x1);
		ry=windowHeight/(range.y2-range.y1);
	}
	
	public static void setWindow(int width, int height ) {
		windowWidth=width;
		windowHeight=height;
	}
	
	public static void setRange(int width, int height ) {
		windowWidth=width;
		windowHeight=height;
	}
	
	public void setSize(float _size) {
		size=_size;
	}
	
	public void setColor(Color _color) {
		color=_color;
	}

	public int nofPixel(double d) {
		return (int)d;
	}
	
	public int xPixel(double d) {
		return (int)(rx*(d-range.x1));
	}

	public int yPixel(double d) {
		return windowHeight- (int)(ry*(d-range.y1));
	}
	
	public List <VBase> setChildrenViews()
	{
		System.out.printf("[ERROR] this code should not reach, define this method in your calss");
		return null;
	}
	
	public void addChildrenToUI(UI ui) {
		if(children!=null) {
			for (VBase v:children)
				ui.addView(v);
		}
	}
	
	public void setUI(UI ui) {
	}
	
	public void draw() {
		if(children!=null) {
			for(VBase vb:children)
				vb.draw();
		}
	}
	
}
