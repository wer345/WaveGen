package Wave;
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


public class AnimWave extends UI {
	private static final long serialVersionUID = 1L;

	private int y = 0;
	WaveData d=new WaveData();
	
	Line l1= new Line(d.crankCenter,d.crankP1);
	Line l2= new Line(d.crankCenter,d.crankP2);
	
	
	public void setup() {
		frametime=20;
	    //frame.setSize(800, 600);
		int pixWindowX=600;
		int pixWindowY=400;
		double originX=0.2; // x position of origin in the Window, 0 - left edge, 1 - right edge
		double originY=0.2; // y position of origin in the Window, 0 - bottom edge, 1 - top edge
		
		double physicXsize=1000; // the physic size in x the windows shows.
		
		setWindow(pixWindowX,pixWindowY);
		
		double windowYXRate=pixWindowY;
		windowYXRate/=pixWindowX;
	    VBase.rangeDefault= new Range(-originX*physicXsize,-originY*physicXsize*windowYXRate,
	    		(1-originX)*physicXsize,(1-originY)*physicXsize*windowYXRate);
		
	    VLine vl1=new VLine(l1);
	    vl1.setColor(Color.blue);
	    vl1.setSize(2);
		addView(vl1);
	    VLine vl2=new VLine(l2);
	    vl2.setColor(Color.green);
	    vl2.setSize(3);
		addView(vl2);
	}

	public void loop()
	{
		d.next();
	}
	
	public static void main(String[] args) {
		start(new AnimWave());
	}
}
