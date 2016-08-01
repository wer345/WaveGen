package physics;

import wave.Geo;

public class Link extends Obj {
	public Point fix;
	public Point free;
	public double r,angle;
	
	public Link() {
		
	}
	
	public Link(Point fix,double r,double angle) {
		this.fix=fix;
		this.r=r;
		this.angle=angle;
		free = new Point(0,0);
		update();
	}

//	public Link(Point fix,Point free) {
//		this.fix=fix;
//		this.r=Geo.distannce(fix, free);
//	}
	
	public void moveTo(double x,double y) {
		fix.x=x;
		fix.y=y;
	}

	public void rotateTo(double angle) {
		this.angle=angle;
	}
	
	public void update() {
		free.x=fix.x+r*Math.cos(angle);
		free.y=fix.y+r*Math.sin(angle);
	}
	
}
