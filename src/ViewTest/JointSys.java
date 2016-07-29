package ViewTest;

import java.awt.Color;
import java.util.ArrayList;

import view.UI;
import view.VBase;
import view.VJoint;
import view.VJointPush;
import view.VLine;
import Physics.Joint;
import Physics.JointPush;
import Physics.Point;

public class JointSys extends VBase {
	Point fixP=new Point(40,0);
	Point freeP=new Point(0,20);
	Point jointP=new Point(40,20);
	Point pushP=new Point(35,-20);
	
//    Joint joint = new Joint(fixP,freeP,jointP);
    JointPush jointPush = new JointPush(fixP,freeP,jointP,pushP);

	Point fixP2=new Point(-30,0);
	Point jointP2=new Point(-30,20);
    Joint joint2 = new Joint(fixP2,freeP,jointP2);
    

	public JointSys(UI ui) {
		super();
		//give view crank
		VLine crank = new VLine(new Point(0,0),freeP,Color.red,6);
		
		// give view of joint 1
	    VJointPush vjoint;
	    vjoint = new VJointPush(jointPush);
	    vjoint.fixColor=Color.green;
	    vjoint.freeColor=Color.cyan;
	    

	    // give view of joint 2
	    VJoint vjoint2;
	    vjoint2 = new VJoint(joint2);
	    joint2.side=Joint.Right;
	    
	    // add all components to children
	    children = new ArrayList <VBase>(); 
	    children.add(crank);
	    children.add(vjoint);
	    children.add(vjoint2);

	    // set up for all components
	    for(VBase vb:children)
	    	vb.setUI(ui);
	}


	// give the driving point (x,y)
	public void draw(double x,double y) {
		freeP.x=x;
		freeP.y=y;
		draw();
	}
	
}
