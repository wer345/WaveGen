package ViewTest;


import view.Range;
import view.UI;
import view.VBase;


public class AnimJoint extends UI {
	private static final long serialVersionUID = 1L;
	double angleMax=2*Math.PI;
	double step=0.01*Math.PI;
	
	JointSys js; //=new JointSys();
	
	double r1=15;
	
	public void setup() {
		frametime=20;
	    //frame.setSize(800, 600);
		setWindow(500,500);
		
	    VBase.rangeDefault= new Range(-50,-50,50,50);
	    js=new JointSys(this);
	}

	double angle=0;
	
	public void loop()
	{
		//change the poistion of 3 points for each frame
		if (angle>angleMax) {
			//stop();
			angle-=angleMax;
		}

		if(js!=null)
			js.draw(r1*Math.cos(angle), r1*Math.sin(angle));
		angle+=step;
	}
	
	public static void main(String[] args) {
		start(new AnimJoint());
	}
}
