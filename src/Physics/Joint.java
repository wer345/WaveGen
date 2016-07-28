package Physics;

import Wave.Geo;

public class Joint extends Point {
	public static int Left=0;
	public static int Right=1;
	
	int side;
	public Point fix = new Point(0,0);
	public Point free = new Point(20,0);
	public Point joint = new Point(10,10);
	double p,q;
	
	public Joint() {
		
	}
	
	public Joint(Point fix,Point free,Point joint ) {
		this.fix=fix.clone();
		this.free=free.clone();
		this.joint=joint.clone();
		set_p_q();
	}
	
	public void set_p_q() {
		double p=Geo.distannce(free, joint);
		double q=Geo.distannce(fix, joint);
	}
	
}
