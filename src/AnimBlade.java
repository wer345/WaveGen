import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import Physics.Line;
import Physics.Point;
import View.Range;
import View.VBase;
import View.VLine;


public class AnimBlade extends Animation {
	private static final long serialVersionUID = 1L;

	private int y = 0;

	double r1=30;
	double r2=20;
	
	//define 3 points
	Point p1= new Point(0,0);
	Point p2= new Point(r1,0);
	Point p3= new Point(r1-r2,0);
	Point p4= new Point(r1+r2,0);
	
	//define the first line is from p1 to p2 
	Line l1= new Line(p1,p2);
	//define the second line is from p2 to p3 
	Line l2= new Line(p3,p4);

	double angleMax=2*Math.PI;
	double step=0.01*Math.PI;
	
	void setup() {
		frametime=20;
	    //frame.setSize(800, 600);
		setWindow(500,500);
	    VBase.rangeDefault= new Range(-50,-50,50,50);
	    

	    // Draw the line of blade point
		Point lastPoint= new Point(r1+r2,0);
		double a=0;
	    while(a<angleMax*2) {
	    	a+=step;
	    	double x=r1*Math.cos(a)+r2*Math.cos(0.5*a);
	    	double y=r1*Math.sin(a)+r2*Math.sin(0.5*a);
			Point p= new Point(x,y);
			views.add(new VLine(new Line(lastPoint,p)));
			lastPoint=p;
	    }
		

//	    double range=0.25*Math.PI;
//	    a=-range;
//	    boolean first=true;
//	    while (a<range) {
//	    	double x=r1*Math.cos(a)+r2*Math.cos(0.5*a);
//	    	double y=r1*Math.sin(a)+r2*Math.sin(0.5*a);
//	    	
//	    	if(first) {
//	    		
//	    	}
//	    }
	    
	    
	    //create the first line and add it to the view
	    VLine vl1=new VLine(l1);
	    vl1.setColor(Color.blue);
	    vl1.setSize(5);
		views.add(vl1);
		
	    //create the second line and add it to the view
	    VLine vl2=new VLine(l2);
	    vl2.setColor(Color.green);
	    vl2.setSize(2);
		views.add(vl2);
	}

	double angle=0;
	void loop()
	{
		//change the poistion of 3 points for each frame
		if (angle>angleMax) {
			angle-=angleMax;
		}
		
		p2.x=r1*Math.cos(angle);
		p2.y=r1*Math.sin(angle);
		double dx=r2*Math.cos(0.5*angle);
		double dy=r2*Math.sin(0.5*angle);
		p3.x=p2.x-dx;
		p3.y=p2.y-dy;
		p4.x=p2.x+dx;
		p4.y=p2.y+dy;
		angle+=step;
	}
	
	public static void main(String[] args) {
		start(new AnimBlade());
	}
}
