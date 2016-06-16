package Wave;

import Physics.Point;

public class Geo {
	
	
	/**
	 * drive point is at the left bottom of the pivot center, driving to the right.
	 * pivot bar is below the pivot center. 
	 * 
	 * @param drive			drive point
	 * @param driveLenght	drive bar length
	 * @param pivotCenter	center of pivot
	 * @param pivotRadius	the radius of pivot
	 * @return return the angle from vertical line and the pivot bar, CWW 
	 */
	public static double angleToRightPivot(Point drive,double driveLenght,Point pivotCenter,double pivotRadius)
	{
		double d_dp=drive.distance(pivotCenter);
		double a_dp_v=Math.asin((drive.x - pivotCenter.x)/d_dp); // the angle between drive-pivotCenter line and vertical line
		double acos= -(driveLenght*driveLenght -d_dp*d_dp - pivotRadius*pivotRadius)/(2*d_dp*pivotRadius);
		double a_dp_p=0;
		if(acos>=-1.0 && acos<=1.0)
			a_dp_p=Math.acos(acos);
		return a_dp_v + a_dp_p;
	}
	
	/**
	 * drive point is at the right above of the pivot center, driving downward.
	 * pivot bar is at the right the pivot center. 
	 * 
	 * @param drive			drive point
	 * @param driveLength	drive bar length
	 * @param pivotCenter	center of pivot
	 * @param pivotRadius	the radius of pivot
	 * @return return the angle from horizontal line and the pivot bar, CWW 
	 */
	public static double angleToLowPivot(Point drive,double driveLength,Point pivotCenter,double pivotRadius)
	{
		double d_dp=drive.distance(pivotCenter);
		double a_dp_h=Math.asin((drive.y - pivotCenter.y)/d_dp); // the angle between drive-pivotCenter line and vertical line
		double acos= -(driveLength*driveLength -d_dp*d_dp - pivotRadius*pivotRadius)/(2*d_dp*pivotRadius);
		double a_dp_p=0;
		if(acos>=-1.0 && acos<=1.0)
			a_dp_p=Math.acos(acos);
		return a_dp_h - a_dp_p;
	}
	
	public static void _main(String[] args) {
		Point drive = new Point(10,19);
		double driveLength=20;
		Point pivotCenter =  new Point(0,0);
		double pivotRadius = 10;
		double a=angleToLowPivot(drive,driveLength,pivotCenter,pivotRadius);
		System.out.printf("A=%f\n", a);
	}
	
}
