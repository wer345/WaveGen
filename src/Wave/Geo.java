package Wave;



import Physics.Point;
import Physics.Vector;

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
	
	
	/**
	 * 有点 A,B，求两个点C，使AC=p,BC=q. 由A走向B，C1是左边点，C2是右边点
	 * @param C1  -- output, left point in the way of A to B
	 * @param C2  -- output, right point in the way of A to B
	 * @param A	  -- point A
	 * @param B	  -- point B	
	 * @param p	  -- distance A to C
	 * @param q	  -- distance B to C
	 */
	public static void getJoint(Point C1,Point C2,Point A,Point B,double p,double q) {
		// C投影到AB于D点，设AD=a,BD=b,CD=c;
		double d2=(A.x-B.x)*(A.x-B.x)+(A.y-B.y)*(A.y-B.y);
		double d=Math.sqrt(d2);
		double pq2=p*p-q*q;
		double k=pq2/d;
		double a=(d+k)/2;
		double b=(d-k)/2;
		double p_a2=p*p-a*a;
		double c=-1;
		if(p_a2>=0) {
			c=Math.sqrt(p_a2);
			Vector A2D = new Vector(B.x-A.x,B.y-A.y);  // Set A to B
			// Set A to D
			A2D.normalize();
			A2D.mul(a);
			
			Vector D2C = new Vector(-(B.y-A.y),B.x-A.x);  // Set A to B
			// Set D to C
			D2C.normalize();
			D2C.mul(c);
			if(C1!=null) {
				C1.x=A.x+A2D.x+D2C.x;
				C1.y=A.y+A2D.y+D2C.y;
			}
			if(C2!=null) {
				C2.x=A.x+A2D.x-D2C.x;
				C2.y=A.y+A2D.y-D2C.y;
			}
		}
	}
	
	public static double edgeLength(double a,double b, double ac) {
		return Math.sqrt(a*a+b*b-2*a*b*Math.sin(ac));
	}
	
	public static void test1() {
		Point A = new Point(-1,-1);
		Point B = new Point(1,1);
		Point C1 = new Point();
		Point C2 = new Point();
		getJoint(C1,C2,A,B,2,2);
		System.out.printf("C1=%s;C2=%s\n", C1,C2);
	}
	
	public static void test2() {
		Point drive = new Point(10,19);
		double driveLength=20;
		Point pivotCenter =  new Point(0,0);
		double pivotRadius = 10;
		double a=angleToLowPivot(drive,driveLength,pivotCenter,pivotRadius);
		System.out.printf("A=%f\n", a);
	}
	
	public static void _main(String[] args) {
		test1();
	}
	
}
