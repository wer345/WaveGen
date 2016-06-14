package Wave;

import Physics.Point;

public class WaveData {
	double driveHeight=100;
	double bottomHeight=0;
	double crankRadius=100;
	
	Point crankCenter= new Point(0,driveHeight);
	
	Point crankP1= new Point();
	Point crankP2= new Point();

	double crankAngle=0;
	double step=0.01*Math.PI;
	double maxAngle=2*Math.PI;
	double angle90=Math.PI/2;
	
	WaveData() {
		start();
	}
	
	public void start()
	{
		crankAngle=0;
		run();
	}
	
	public void run() {
		crankP1.set(
				crankCenter.x + crankRadius*Math.cos(crankAngle),
				crankCenter.y + crankRadius*Math.sin(crankAngle));
		crankP2.set(
				crankCenter.x +crankRadius*Math.cos(crankAngle+angle90),
				crankCenter.y + crankRadius*Math.sin(crankAngle+angle90));
	}
	
	public boolean next()
	{
		double newCrankAngle = crankAngle + step;
		if(newCrankAngle <= maxAngle) {
			crankAngle = newCrankAngle;
			run();
			return true;
		}
		return false;
	}
}
