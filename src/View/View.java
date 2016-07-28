package View;

import java.lang.reflect.Method;

import Physics.Obj;


public class View <T> extends VBase {
	public T value;
	
	public View() {
	}

	public View(T v,UI ui) {
		value=v;
	    getChildren();
	    addChildrenToUI(ui);
	}
	
	public void set(T v) {
		value=v;
	}

	
	public void loop() {
		Obj obj = (Obj) value;
		obj.update();
	}
	
}
