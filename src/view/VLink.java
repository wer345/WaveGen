package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Joint;
import physics.Line;
import physics.Link;
import physics.Point;

public class VLink extends View <Link>{
	public float size=2;
	public Color color=Color.cyan;
	
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
			
		}
		
		return children;
	}
	
}
