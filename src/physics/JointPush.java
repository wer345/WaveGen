package physics;

import wave.Angle;
import wave.Geo;

public class JointPush extends Obj {
	public static int Left=0;
	public static int Right=1;
	
	public int side=Left;
	public Point fix = null;
	public Point free = null;
	public Point joint = null;
	public Point push = null;
	
	public double p,q;
	public double a_push=Angle.d2r(45);
	public double l_push=30;
	
	public JointPush() {
		
	}

	public JointPush(Point fix,Point free,double p,double q,int side,double angle,double l_push) {
		this.fix=fix;
		this.free=free;
		this.joint=new Point();
		this.push=new Point();
		this.p=p;
		this.q=q;
		this.side=side;
		a_push=angle;
		this.l_push=l_push;
	}
	
	// Create a Joint that structure is set up on 3 points
	// the free point can move afterward
	public JointPush(Point fix,Point free,Point joint) {
		side=Left;
		this.fix=fix;
		this.free=free;
		this.joint=joint;
		this.push=new Point();
		set_p_q();
	}
	
	public void set_p_q() {
		p=Geo.distannce(free, joint);
		q=Geo.distannce(fix, joint);
//		p1=Geo.distannce(joint,push);
//		q1=Geo.distannce(push, fix);
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
		
//		Geo.getJoint(push, null, joint, fix, p1, q1);
		double angle=Math.atan2(joint.y-fix.y, joint.x-fix.x);
		angle -= a_push;
		push.set(fix.x+l_push*Math.cos(angle),fix.y+l_push*Math.sin(angle));
	}
	
	
}
