package Wave;

import Physics.Point;

public class Geo {
	public static double pivotAngle(Point drive,double driveLenght,Point pivotCenter,double pivotRadius)
	{
		double d_dp=drive.distance(pivotCenter);
		double a_dp_v=Math.asin((drive.x - pivotCenter.x)/d_dp); // the angle between drive-pivotCenter line and vertical line
		double acos= -(driveLenght*driveLenght -d_dp*d_dp - pivotRadius*pivotRadius)/(2*d_dp*pivotRadius);
		double a_dp_p=0;
		if(acos>=-1.0 && acos<=1.0)
			a_dp_p=Math.acos(acos);
		return a_dp_v + a_dp_p;
	}
}
