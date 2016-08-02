package physics;

import wave.Angle;

public class JointPosition {
	
	public double crankAngle;
	public double x,y;
	
	public JointPosition(double crankAngle, double x, double y) {
		super();
		this.crankAngle = crankAngle;
		this.x = x;
		this.y = y;
	}

	public void set(double crankAngle, double x, double y) {
		this.crankAngle = crankAngle;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return String.format("%6.1f,%6.2f,%6.2f",Angle.r2d(crankAngle),x,y);
	}
	
}
