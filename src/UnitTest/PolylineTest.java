package UnitTest;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import view.Range;
import view.UI;
import view.VBase;
import view.VLine;
import view.VPolyline;
import Physics.Line;
import Physics.Point;
import Physics.Polyline;


public class PolylineTest extends UI {
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
	    VBase.rangeDefault= new Range(-0.1*range,-0.2,1.1*range,1.0);
	    

	    // show the grid
		
	    for(double y=0;y<=1;y+=0.1) {
	    	addView(new VLine(0,y,range,y));
	    }

	    // show the curve
	    double c=0.5;
	    for(c=0.2;c<=0.5;c+=0.02) {
		    double a=0;
			Polyline pl=new Polyline();
		    while (a<range) {
		    	double x=Math.cos(a)-c*Math.cos(2*a);
		    	double y=Math.sin(a)-c*Math.sin(2*a);
		    	double gap=Math.sqrt(square(x-c)+y*y);
		    	pl.add(a,gap);
		    	a+=step;
		    }
		    VPolyline vpl= new VPolyline(pl);
		    vpl.setColor(Color.blue);
		    addView(vpl);
	    }
	}

	// Note: there is no loop()
	public static void main(String[] args) {
		start(new PolylineTest());
	}
}
