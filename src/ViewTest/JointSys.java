package ViewTest;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import view.UI;
import view.VBase;
import view.VJoint;
import view.VLine;
import Physics.Joint;
import Physics.Point;

public class JointSys extends VBase {
	Point fixP=new Point(40,0);
	Point freeP=new Point(0,20);
	Point jointP=new Point(40,20);
    Joint joint = new Joint(fixP,freeP,jointP);

	Point fixP2=new Point(-30,0);
	Point jointP2=new Point(-30,20);
    Joint joint2 = new Joint(fixP2,jointP,jointP2);
    
    VJoint vjoint,vjoint2;

	public JointSys(UI ui) {
		super();
		
		VLine crank = new VLine(new Point(0,0),freeP,Color.red,6);
	    vjoint = new VJoint(joint);
	    vjoint.fixColor=Color.green;
	    vjoint.freeColor=Color.cyan;
	    vjoint2 = new VJoint(joint2);
	    
	    joint2.side=Joint.Right;
	    children = new ArrayList <VBase>(); 
	    
	    children.add(crank);
	    children.add(vjoint);
	    children.add(vjoint2);
	    
	    for(VBase vb:children)
	    	vb.setUI(ui);
	}

	
	public void update(double x,double y) {
		freeP.x=x;
		freeP.y=y;
		update();
		
	}
	
}
