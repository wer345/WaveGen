package ViewTest;


import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import Physics.Joint;
import Physics.Line;
import Physics.Point;
import View.UI;
import View.Range;
import View.VBase;
import View.VJoint;
import View.VLine;


public class AnimJoint extends UI {
	private static final long serialVersionUID = 1L;

	private int y = 0;

	double r1=30;
	double r2=20;
	
	double angleMax=2*Math.PI;
	double step=0.01*Math.PI;

    Joint jt = new Joint();
    VJoint vj = new VJoint();
	
	public void setup() {
		frametime=20;
	    //frame.setSize(800, 600);
		setWindow(500,500);
	    VBase.rangeDefault= new Range(-50,-50,50,50);
	    
		
	    vj.set(jt);
	    vj.getChildren();
	    
	    vj.addChildrenToUI(this);
	    
	}

	double angle=0;
	
	public void loop()
	{
		//change the poistion of 3 points for each frame
		if (angle>angleMax) {
			stop();
			//angle-=angleMax;
		}
		
		vj.value.joint.y+=1;
		angle+=step;
	}
	
	public static void main(String[] args) {
		start(new AnimJoint());
	}
}
