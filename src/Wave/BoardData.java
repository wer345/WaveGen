package Wave;

import Physics.Point;

public class BoardData {
	public double driverBarLength;	// length of pivot drive
	public double driverBarPositionAngle;		// the angle between the syn bar and the pivot drive
	public double pusherLength;	// the length of the drive from pivot to board
	public  double boardLength;			// the length of board
	
	public Point driverAxis;				// the point the board rotate around
	public double driverAngle; 				// angle of the sync bar
	public Point driverEnd=new Point(); //  end of the pivot drive 
	public Point boardStart=new Point(); 	// start of the board
	public Point boardEnd=new Point(); 	// end of the board
	
	// DriverAxis X, DriverAxis Y, DriveBarLength, DriveBarPosition, PusherLength, boardLength			
	
	BoardData(
		double _driverAxisX, 
		double _driverAxisY, 
		double _driverBarLength,  // negative value for negative drive
		double _driverBarPositionAngle,
		double _pusherLength,
		double _boardLength
		)
		{
			driverAxis = new Point(_driverAxisX,_driverAxisY);
			driverBarLength = _driverBarLength;
			driverBarPositionAngle = _driverBarPositionAngle;
			pusherLength = _pusherLength;
			boardLength = _boardLength;
		}
	
	void run(Point boardFix,double angle) {
		boardStart.x=boardFix.x;
		boardStart.y=boardFix.y;
		driverAngle=angle;
		double angleDriver=driverAngle+driverBarPositionAngle;
		driverEnd.set(
			driverAxis.x+driverBarLength*Math.sin(angleDriver),
			driverAxis.y-driverBarLength*Math.cos(angleDriver));
		double angleboard= Geo.angleToLowPivot(driverEnd,pusherLength,boardStart,boardLength);
		boardEnd.set(boardStart.x+boardLength*Math.cos(angleboard), boardStart.y+boardLength*Math.sin(angleboard));
	}
}
