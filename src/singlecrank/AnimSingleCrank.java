package singlecrank;


import view.Range;
import view.UI;
import view.VBase;


public class AnimSingleCrank extends UI {
	private static final long serialVersionUID = 1L;
	double angleMax=2*Math.PI;
	double step=0.01*Math.PI;
	
	VSingleCrank js; //=new JointSys();
	
	
	public void setup() {
		
		frametime=40;	// minisecond per frame
	    //frame.setSize(800, 600);
		int pixWindowWidth=1400;
		int pixWindowHeight=600;
		int pixWindowPosX=400;
		int pixWindowPosY=600;

		boolean smallWindows=true;
		if(smallWindows) {
			pixWindowWidth=700;
			pixWindowHeight=250;
			pixWindowPosX=1000;
			pixWindowPosY=900;
		}
		
		double originX=0.25; // x position of origin in the Window, 0 - left edge, 1 - right edge
		double originY=0.0; // y position of origin in the Window, 0 - bottom edge, 1 - top edge
		
		double physicXsize=500; // the physic size in x the windows shows.
		
		setWindow(pixWindowWidth,pixWindowHeight,pixWindowPosX,pixWindowPosY);
		
		double windowYXRate=pixWindowHeight;
		windowYXRate/=pixWindowWidth;
	    VBase.rangeDefault= new Range(-originX*physicXsize,-originY*physicXsize*windowYXRate,
	    		(1-originX)*physicXsize,(1-originY)*physicXsize*windowYXRate);
		
	    js=new VSingleCrank(this);
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
