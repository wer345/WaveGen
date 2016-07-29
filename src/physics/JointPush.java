package physics;

import wave.Geo;

public class JointPush extends Point {
	public static int Left=0;
	public static int Right=1;
	
	public int side=Left;
	public Point fix = null;
	public Point free = null;
	public Point joint = null;
	public Point push = null;
	
	double p,q,p1,q1;
	
	public JointPush() {
		
	}
	
	// Create a Joint that structure is set up on 3 points
	// the free point can move afterward
	public JointPush(Point fix,Point free,Point joint,Point push ) {
		side=Left;
		this.fix=fix;
		this.free=free;
		this.joint=joint;
		this.push=push;
		set_p_q();
	}
	
	public void set_p_q() {
		p=Geo.distannce(free, joint);
		q=Geo.distannce(fix, joint);
		p1=Geo.distannce(joint,push);
		q1=Geo.distannce(push, fix);
	}
	
	public void moveTo(double x,double y) {
		free.x=x;
		free.y=y;
	}
	
	public void update() {
		if(side==Left) 
			Geo.getJoint(joint, null, free, fix, p, q);
		else
			Geo.getJoint(null, joint, free, fix, p, q);
		
		Geo.getJoint(push, null, joint, fix, p1, q1);
		
	}
	
	
}
