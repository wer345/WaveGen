package singlecrank;

import java.awt.Color;
import java.util.ArrayList;

import physics.Joint;
import physics.JointPush;
import physics.Point;
import view.UI;
import view.VBase;
import view.VJoint;
import view.VJointPush;
import view.VLine;

public class VSigleCrank extends VBase {
	SysSigleCrank sys;

	public VSigleCrank(UI ui) {
		super();
		sys = new SysSigleCrank();
		
		//give view crank
		VLine crank = new VLine(new Point(sys.x_crankCenter,sys.y_crankCenter),sys.freeP,Color.red,6);
		
		// give view of joint 1
	    VJointPush vjoint = new VJointPush(sys.jointPush);
	    vjoint.fixColor=Color.green;
	    vjoint.freeColor=Color.cyan;
	    

	    // give view of joint 2
	    VJoint vjoint2= new VJoint(sys.joint2);
	    
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
		sys.update(x,y);
		draw();
	}
	
}
