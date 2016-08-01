package singlecrank;


import view.Range;
import view.UI;
import view.VBase;


public class AnimSingleCrank extends UI {
	private static final long serialVersionUID = 1L;
	double angleMax=2*Math.PI;
	double step=0.01*Math.PI;
	
	VSigleCrank js; //=new JointSys();
	
	
	public void setup() {
		
		frametime=40;	// minisecond per frame
	    //frame.setSize(800, 600);
		int pixWindowX=1400;
		int pixWindowY=600;
		
		double originX=0.25; // x position of origin in the Window, 0 - left edge, 1 - right edge
		double originY=0.0; // y position of origin in the Window, 0 - bottom edge, 1 - top edge
		
		double physicXsize=500; // the physic size in x the windows shows.
		
		setWindow(pixWindowX,pixWindowY,400,600);
		
		double windowYXRate=pixWindowY;
		windowYXRate/=pixWindowX;
	    VBase.rangeDefault= new Range(-originX*physicXsize,-originY*physicXsize*windowYXRate,
	    		(1-originX)*physicXsize,(1-originY)*physicXsize*windowYXRate);
		
	    js=new VSigleCrank(this);
	}

	double angle=0;
	
	public void loop()
	{
		//change the poistion of 3 points for each frame
		if (angle>angleMax) {
			//stop();
			angle-=angleMax;
		}

		if(js!=null) {
			double r1=js.sys.r_crank;
			//js.draw(r1*Math.cos(angle), r1*Math.sin(angle));
			js.rotate(angle);
		}
		angle+=step;
	}
	
	public static void main(String[] args) {
		start(new AnimSingleCrank());
	}
}
