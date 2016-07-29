package view;

import physics.Obj;


public class View <T> extends VBase {
	public T value;
	
	public View() {
	}

	public View(T v) {
		value=v;
	}

	public void setUI(UI ui) {
		// call the setChildrenViews method in the descent class  to set up children views
	    setChildrenViews();
	    // add the children views to the UI
	    addChildrenToUI(ui);
	}
	
	public void set(T v) {
		value=v;
	}

	
	public void draw() {
		Obj obj = (Obj) value;
		obj.update();
		super.draw();
	}
	
}
