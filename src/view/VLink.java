package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Joint;
import physics.Line;
import physics.Link;
import physics.Point;

public class VLink extends View <Link>{
	public float size=5;
	public Color color=Color.black;
	
	public float fixsize=-1;
	public Color fixcolor=Color.black;

	public float freesize=-1;
	public Color freecolor=Color.black;
	
	public VLink(Link v) {
		super(v);
	}
	
	@Override
	public List <VBase> setChildrenViews()
	{
		if(children==null) {
			children = new ArrayList<VBase>();
			VLine vLink=new VLine(value.fix,value.free,color,size);
			children.add(vLink);
			if(fixsize>0) {
				VCircle vc= new VCircle(value.fix,fixcolor,fixsize);
				children.add(vc);
			}
			if(freesize>0) {
				VCircle vc= new VCircle(value.free,freecolor,freesize);
				children.add(vc);
			}
			
		}
		return children;
	}
	
}
