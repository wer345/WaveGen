package Motion;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import view.Range;
import view.UI;
import view.VBase;
import view.VLine;
import Physics.Line;
import Physics.Point;


public class AnimTest1 extends UI {
	private static final long serialVersionUID = 1L;

	private int y = 0;

	Point p1= new Point(100,100);
	Point p2= new Point(200,200);
	Point p3= new Point(300,100);
	Line l1= new Line(p1,p2);
	Line l2= new Line(p2,p3);
	
	public void setup() {
		frametime=20;
	    //frame.setSize(800, 600);
		setWindow(600,400);
	    VBase.rangeDefault= new Range(100,-1000,350,4000);
		
	    VLine vl1=new VLine(l1);
	    vl1.setColor(Color.blue);
	    vl1.setSize(5);
		addView(vl1);
	    VLine vl2=new VLine(l2);
	    vl2.setColor(Color.green);
	    vl2.setSize(10);
		addView(vl2);
	}

	public void loop()
	{
		p2.y+=5;
		if(p2.y>300)
			p2.y=0;
		p3.y+=1;
	}
	
	public static void main(String[] args) {
		start(new AnimTest1());
	}
}
