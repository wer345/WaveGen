package View;

import java.util.ArrayList;
import java.util.List;

import Physics.Joint;
import Physics.Line;
import Physics.Point;

public class VJoint extends View <Joint>{

	
	
	public VJoint(Joint v,UI ui) {
		super(v,ui);
	}
	
	@Override
	public List <VBase> getChildren()
	{
		if(children==null) {
			children = new ArrayList<VBase>();
			children.add(new VLine(new Line(value.fix,value.joint)));
			children.add(new VLine(new Line(value.free,value.joint)));
		}
		
		return children;
	}
	
	public void update() {
		value.joint.y+=1;
	}
	
//	@Override
//	public void loop() {
//		value.joint.y+=1;	
//	}
	
	
}
