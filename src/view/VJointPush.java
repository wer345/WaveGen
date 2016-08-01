package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Joint;
import physics.JointPush;
import physics.Line;
import physics.Point;

public class VJointPush extends View <JointPush>{

	
	public float fixSize=2;
	public Color fixColor=Color.red;

	public float freeSize=4;
	public Color freeColor=Color.blue;
	
	public float pushSize=4;
	public Color pushColor=Color.green;
	
	public float fixNodeSize=10;
	public Color fixNodecolor=Color.black;
	
	public VJointPush(JointPush v) {
		super(v);
	}
	
	
	@Override
	public List <VBase> setChildrenViews()
	{
		if(children==null) {
			
			children = new ArrayList<VBase>();

			// The arm connected to the fix point
			VLine line2Fix=new VLine(value.fix,value.joint,fixColor,fixSize);
			children.add(line2Fix);
			
			// the arm connected to the free point
			VLine line2Free= new VLine(value.free,value.joint,freeColor,freeSize);
			children.add(line2Free);

			// the arm connected to the push point
			VLine line2Push= new VLine(value.fix,value.push,pushColor,pushSize);
			children.add(line2Push);
			
			if(fixNodeSize>0) {
				VCircle vc= new VCircle(value.fix,fixNodecolor,fixNodeSize);
				children.add(vc);
			}
			
		}
		
		return children;
	}
	
	
}
