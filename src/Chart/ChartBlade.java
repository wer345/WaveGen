package Chart;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import Physics.Line;
import Physics.Point;
import View.UI;
import View.Range;
import View.VBase;
import View.VLine;


public class ChartBlade extends UI {
	private static final long serialVersionUID = 1L;

	private int y = 0;

	double r1=30;
	double r2=22;
	
	double angleMax=2*Math.PI;
	double step=0.01*Math.PI;
	
	public void setup() {
	    double range=0.25*Math.PI;
		frametime=20;
	    //frame.setSize(800, 600);
		setWindow(700,500);
	    VBase.rangeDefault= new Range(-1.1*range,0,1.1*range,50);
	    

	    // show the grid
		
	    for(double y=0;y<=50;y+=10) {
	    	addView(new VLine(-range,y,range,y));
	    }

	    // show the curve
	    double a=-range;
	    boolean first=true;
	    Point lastPoint= new Point(r1+r2,0);	    
	    while (a<range) {
	    	double x=r1*Math.cos(a)+r2*Math.cos(0.5*a);
	    	double y=r1*Math.sin(a)+r2*Math.sin(0.5*a);
	    	double d1=Math.sqrt(x*x+y*y);
	    	
	    	double a2=a+Math.PI;
	    	if(a<0) {
		    	x=r1*Math.cos(a2)+r2*Math.cos(0.5*a2);
		    	y=r1*Math.sin(a2)+r2*Math.sin(0.5*a2);
	    	}
	    	else {
		    	x=r1*Math.cos(a2)-r2*Math.cos(0.5*a2);
		    	y=r1*Math.sin(a2)-r2*Math.sin(0.5*a2);
	    	}
	    	double d2=Math.sqrt(x*x+y*y);
	    	
	    	double d=d1-d2;
	    	
	    	if(first) {
	    		first=false;
	    		lastPoint= new Point(a,d);
	    	}
	    	else {
	    		Point p=new Point(a,d);
	    		addView(new VLine(new Line(lastPoint,p)));
	    		lastPoint=p;
	    	}
	    	a+=step;
	    }
	    
	}

	// Note: there is no loop()
	public static void main(String[] args) {
		start(new ChartBlade());
	}
}
