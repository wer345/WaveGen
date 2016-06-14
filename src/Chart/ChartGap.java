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


public class ChartGap extends UI {
	private static final long serialVersionUID = 1L;

	private int y = 0;

	double r1=30;
	double r2=22;
	
	double angleMax=2*Math.PI;
	
	static double square(double x)
	{
		return x*x;
	}
	
	public void setup() {
	    double range=0.25*Math.PI;
		double step=0.01*range;
		frametime=20;
	    //frame.setSize(800, 600);
		setWindow(700,500);
	    VBase.rangeDefault= new Range(-1.1*range,-0.1,1.1*range,1.1);
	    

	    // show the grid
		
	    for(double y=0;y<=1;y+=0.1) {
	    	addView(new VLine(-range,y,range,y));
	    }

	    // show the curve
	    double a=-range;
	    double c=0.5;
	    
	    boolean first=true;
	    Point lastPoint= new Point(r1+r2,0);	    
	    while (a<range) {
	    	double x=Math.cos(a)-c*Math.cos(2*a);
	    	double y=Math.sin(a)-c*Math.sin(2*a);
	    	double gap=Math.sqrt(square(x-c)+y*y);
	    	
	    	
	    	if(first) {
	    		first=false;
	    		lastPoint= new Point(a,gap);
	    	}
	    	else {
	    		Point p=new Point(a,gap);
	    		addView(new VLine(new Line(lastPoint,p)));
	    		lastPoint=p;
	    	}
	    	a+=step;
	    }
	    
	}

	// Note: there is no loop()
	public static void main(String[] args) {
		start(new ChartGap());
	}
}
