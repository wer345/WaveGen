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
	JointSys js=new JointSys();
	
	double r1=10;
	
	double angleMax=2*Math.PI;
	double step=0.01*Math.PI;

	Point fixP=new Point(40,0);
	Point freeP=new Point(0,20);
	Point jointP=new Point(40,20);
    Joint joint = new Joint(fixP,freeP,jointP);

	Point fixP2=new Point(-40,0);
	Point jointP2=new Point(-40,20);
    Joint joint2 = new Joint(fixP2,jointP,jointP2);
    
    VJoint vjoint,vjoint2;
	
	public void setup() {
		frametime=20;
	    //frame.setSize(800, 600);
		setWindow(500,500);
		
	    VBase.rangeDefault= new Range(-50,-50,50,50);
	    
	    vjoint = new VJoint(joint,this);
	    vjoint2 = new VJoint(joint2,this);
	    joint2.side=Joint.Right;
	}

	double angle=0;
	
	public void loop()
	{
		//change the poistion of 3 points for each frame
		if (angle>angleMax) {
			//stop();
			angle-=angleMax;
		}
		
		freeP.x=r1*Math.cos(angle);
		freeP.y=r1*Math.sin(angle);

		vjoint.loop();
		vjoint2.loop();
		
		angle+=step;
	}
	
	public static void main(String[] args) {
		start(new AnimJoint());
	}
}
