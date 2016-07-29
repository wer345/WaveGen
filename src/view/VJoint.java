package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Joint;
import physics.Line;
import physics.Point;

public class VJoint extends View <Joint>{

	
	public float fixSize=2;
	public Color fixColor=Color.red;

	public float freeSize=4;
	public Color freeColor=Color.blue;
	
	
	public VJoint(Joint v) {
		super(v);
	}
	
	
	@Override
	public List <VBase> setChildrenViews()
	{
		if(children==null) {
			
			children = new ArrayList<VBase>();

			VLine line2Fix=new VLine(value.fix,value.joint,fixColor,fixSize);
			children.add(line2Fix);
			
			VLine line2Free= new VLine(value.free,value.joint,freeColor,freeSize);
			children.add(line2Free);
		}
		
		return children;
	}
	
	
}
