package Wave;
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


public class AnimWave extends UI {
	private static final long serialVersionUID = 1L;

	private int y = 0;
	Design d=new Design();
	
	
	public void setup() {
		frametime=40;	// minisecond per frame
	    //frame.setSize(800, 600);
		int pixWindowX=1400;
		int pixWindowY=600;
		double originX=0.2; // x position of origin in the Window, 0 - left edge, 1 - right edge
		double originY=0.0; // y position of origin in the Window, 0 - bottom edge, 1 - top edge
		
		double physicXsize=400; // the physic size in x the windows shows.
		
		setWindow(pixWindowX,pixWindowY);
		
		double windowYXRate=pixWindowY;
		windowYXRate/=pixWindowX;
	    VBase.rangeDefault= new Range(-originX*physicXsize,-originY*physicXsize*windowYXRate,
	    		(1-originX)*physicXsize,(1-originY)*physicXsize*windowYXRate);
		
	    double btmPos=43.0;
	    Line bottom = new Line(new Point(0,btmPos),new Point(300,btmPos));
	    VLine vlb=new VLine(bottom);
	    vlb.setColor(Color.blue);
	    vlb.setSize(1);
		addView(vlb);
		
		List<VLine> lineViews = d.getLineViews();
		for(VLine lv:lineViews) {
			addView(lv);
		}
		
		boolean searchForProfile=false;
		
		if(searchForProfile)
			d.findProfile();
	    
	    List<VPolyline> pls = d.getPolylineViews();
	    for(VPolyline pl:pls)
	    	addView(pl);
	}

	public void loop()
	{
		if(!paused) {
			if(!d.next())
				d.start();
		}
	}
	
	public static void main(String[] args) {
		start(new AnimWave());
	}
}
