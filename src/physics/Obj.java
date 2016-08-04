package physics;

import java.util.ArrayList;
import java.util.List;

public class Obj {
	public String name=null;
	public List <Obj> children=null;

	public void addObj(Obj obj) {
		if(children==null) 
			children= new ArrayList<Obj>();
		children.add(obj);
	}
	public void update() {
		if(children!=null) {
			for(Obj obj:children)
				obj.update();
		}
		
	}
	
}
