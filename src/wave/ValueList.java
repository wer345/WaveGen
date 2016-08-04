package wave;
import java.util.ArrayList;


public class ValueList  extends ArrayList <Double>{

	private static final long serialVersionUID = 1L;

	public ValueList() {
		super();
	}
	
	public ValueList(double... args) {
		super();
		for(double d:args)
			add(d);
	}
	
//	ValueList(int n, double v) {
//		super();
//		for(int i=0;i<n;i++)
//			add(v);
//	}

//	ValueList(int n, double v,double inc) {
//		super();
//		for(int i=0;i<n;i++) {
//			add(v);
//			v+=inc;
//		}
//	}

	public void setLinear(int n, double v,double inc) {
		for(int i=0;i<n;i++) {
			add(v);
			v+=inc;
		}
	}
	
	void set(double... args) {
		clear();
		for(double d:args)
			add(d);
	}
	
	void append( double... args) {
		for(double d:args)
			add(d);
	}
	
	
	public double sum() {
		double rst=0;
		for(int i=0;i<size();i++) {
			rst+=get(i);
		}
		return rst;
	}
	
	public ValueList clone() {
		ValueList rst=new ValueList();
		for(Double d:this)
			rst.add(d);
		return rst;
	}

	// add a list
	public void add(ValueList d) {
		if(size()==d.size()) {
			for(int i=0;i<size();i++) {
				Double v=get(i);
				set(i,v+d.get(i));
			}
		}
	}
	
	// add an item
	public void add(int idx, double value) {
		if(idx>=0 && idx<size()) {
			Double v=get(idx);
			set(idx,v+value);
		}
	}

	// sub a list
	public void sub(ValueList d) {
		if(size()==d.size()) {
			for(int i=0;i<size();i++) {
				Double v=get(i);
				set(i,v-d.get(i));
			}
		}
	}
	

	//sub an item
	public void sub(int idx, double value) {
		if(idx>=0 && idx<size()) {
			Double v=get(idx);
			set(idx,v-value);
		}
	}
	
	// dot mul an list
	public void mul(ValueList d) {
		if(size()==d.size()) {
			for(int i=0;i<size();i++) {
				Double v=get(i);
				set(i,v*d.get(i));
			}
		}
	}
	
	// mul an item
	public void mul(int idx, double value) {
		if(idx>=0 && idx<size()) {
			Double v=get(idx);
			set(idx,v*value);
		}
	}
	
	// mul all
	public void mul(double d) {
		for(int i=0;i<size();i++) {
			Double v=get(i);
			set(i,v*d);
		}
	}


	public static ValueList add(ValueList a,ValueList b) {
		if(a.size()==b.size()) {
			ValueList r=a.clone();
			r.add(b);
			return r;
		}
		return null;
	}

	public static ValueList sub(ValueList a,ValueList b) {
		if(a.size()==b.size()) {
			ValueList r=a.clone();
			r.sub(b);
			return r;
		}
		return null;
	}
	
	public static ValueList mul(ValueList a,ValueList b) {
		if(a.size()==b.size()) {
			ValueList r=a.clone();
			r.mul(b);
			return r;
		}
		return null;
	}
	
	
	@Override
	public String toString() {
		return toString("8.4");
	}
	
	public String toString(String fmt) {
		String rst=null;
		for( Double v:this) {
			if(rst==null)
				rst=String.format("[%"+fmt+"f", v);
			else
				rst+=String.format(" ,%"+fmt+"f", v);
		}
		rst+="]";
		
		return rst;
	}

	public static void main(String[] args) {
		//getPusherLength();
		ValueList d1= new ValueList(1.1,2.2);
		d1.mul(d1);
		System.out.printf("d1=%s\n", d1);
		
		ValueList d2= new ValueList();
		d2.setLinear(2,1,2);
		System.out.printf("d2=%s\n", d2);
		d1.set(1.1,2.2);
		d1.mul(d2);
		System.out.printf("d1=%s\n", d1);
	}
}
