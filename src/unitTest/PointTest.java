package unitTest;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import physics.Line;
import physics.Point;
import view.Range;
import view.UI;
import view.VBase;
import view.VLine;
import view.VPoint;


public class PointTest extends UI {
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
	    double range=10;
		double step=0.1*range;
		frametime=20;
	    //frame.setSize(800, 600);
		setWindow(500,500);
		setLocation(500, 300);
	    VBase.rangeDefault= new Range(-1,-1,11,11);
	    

	    // show the grid
		
	    for(double s=0;s<=10;s+=1) {
	    	addView(new VLine(0,s,range,s));
	    	addView(new VLine(s,0,s,range));
	    }

	    VPoint vp = new VPoint(1,1);
	    
	}

	// Note: there is no loop()
	public static void main(String[] args) {
		start(new PointTest());
	}
}
