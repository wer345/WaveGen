package Wave;

import Physics.Point;

public class WaveData {
	double driveHeight=100;
	double bottomHeight=0;
	double crankRadius=20;
	
	Point crankCenter= new Point(0,driveHeight);
	Point crankP1= new Point();
	Point crankP2= new Point();

	double pivotX=100;
	double pivotY=130;
	double pivotRadius=30;
	double pivotDriveLength=100;
	
	Point pivotCenter= new Point(pivotX,pivotY);
	Point pivotP1= new Point();
	Point pivotP2= new Point();
	
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
		
		pivotP1.set(crankP1.x+pivotDriveLength,crankP1.y);
		pivotP2.set(crankP2.x+pivotDriveLength,crankP2.y);
		
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
