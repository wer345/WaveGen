package singlecrank;

import java.awt.Color;
import java.util.ArrayList;

import physics.ContactBoard;
import physics.Joint;
import physics.JointPush;
import physics.Link;
import physics.Point;
import view.UI;
import view.VBase;
import view.VContactBoard;
import view.VJoint;
import view.VJointPush;
import view.VLine;
import view.VLink;

public class VSingleCrank extends VBase {
	
	SysSigleCrank sys;
	SingleDesign design;

	public VSingleCrank(UI ui) {
		super();
		sys = new SysSigleCrank();
		design = new SingleDesign(sys);
		design.findProfile();
		//give view crank
		
		//VLine crank = new VLine(new Point(sys.x_crankCenter,sys.y_crankCenter),sys.crank.free,Color.red,6);
		VLink vcrank = new VLink(sys.crank);
		
		vcrank.fixsize=30;
		vcrank.freesize=20;
		vcrank.freecolor=Color.red;
		
		// give view of joint 1
	    VJointPush vjoint = new VJointPush(sys.jSync1);
	    vjoint.fixColor=Color.green;
	    vjoint.freeColor=Color.cyan;
	    

	    // give view of joint 2
	    VJointPush vjoint2= new VJointPush(sys.jSync2);
	    
	    // add all components to children
	    children = new ArrayList <VBase>(); 
	    children.add(vcrank);
	    children.add(vjoint);
	    children.add(vjoint2);
	    for(JointPush jp:sys.swings) {
	    	VJointPush vjointpush= new VJointPush(jp);
	    	children.add(vjointpush);
	    }
	    
	    if(sys.hasComp) {
	    	VJointPush comp= new VJointPush(sys.compBoard);
		    children.add(comp);
	    }
	    for(ContactBoard bd:sys.boards) {
	    	VContactBoard vbd= new VContactBoard(bd);
	    	children.add(vbd);
	    }

	    double bottomHeight=43;
	    double rightEdge=sys.swings[sys.nofBoard-1].fix.x;
		Link btm = new Link(new Point(0,bottomHeight),rightEdge,0);
		VLink vbtm= new VLink(btm);
		vbtm.size=1;
		vbtm.color=Color.black;
		children.add(vbtm);
	    // set up for all components
	    for(VBase vb:children)
	    	vb.setUI(ui);
	}

	// give the driving point (x,y)
//	public void draw(double x,double y) {
//		sys.moveTo(x,y);
//		draw();
//	}
	
	public void rotate(double angle) {
		sys.rotate(angle);
		draw();
	}
	
}
