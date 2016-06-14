package Chart;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import Physics.Line;
import Physics.Point;
import Physics.Polyline;
import View.UI;
import View.Range;
import View.VBase;
import View.VLine;
import View.VPolyline;


public class MaxPower extends UI {
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
	    double range=5;
		double step=0.01*range;
		frametime=20;
	    //frame.setSize(800, 600);
		setWindow(700,500);
	    VBase.rangeDefault= new Range(-0.2,-0.2,	5.5 ,4.5);
	    

	    // show the grid
		
	    for(double y=-0;y<=4;y+=0.5) {
	    	addView(new VLine(0,y,range,y));
	    }

	    for(double x=-0;x<=5;x+=1) {
	    	addView(new VLine(x,0,x,4));
	    }
	    // show the curve
	    double d=0.4;
	    double k;
	    double g=9.81;
	    for(k=0;k<=1.0;k+=0.1) {
	    	double a=k*k/(2*g*d);
		    double v=0;
			Polyline pl=new Polyline();
			double x;
			double y;
		    do{
		    	x=v;
		    	y=v*(1-a*v*v);
		    	pl.add(x,y);
		    	v+=step;
		    } while(y>-0.1 && v<range);
		    VPolyline vpl= new VPolyline(pl);
		    vpl.setColor(Color.blue);
		    vpl.setSize(2);
		    addView(vpl);
		    double maxV=Math.sqrt(1/(3*a));
		    double maxP=maxV*(1-a*maxV*maxV);
		    VLine maxLine=new VLine(maxV,0,maxV,maxP);
		    maxLine.setColor(Color.green);
		    addView(maxLine);
	    }
	}

	// Note: there is no loop()
	public static void main(String[] args) {
		start(new MaxPower());
	}
}
