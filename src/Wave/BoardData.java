package Wave;

import Physics.Point;

public class BoardData {
	double pivotDriveLength;	// length of pivot drive
	double pivotDriveAngle;		// the angle between the syn bar and the pivot drive
	double boardDriveLength;	// the length of the drive from pivot to board
	double boardLength;			// the length of board
	
	double angle; // angle of the sync bar
	Point pivot;
	Point boardS=new Point(); // start of the board
	Point boardE=new Point(); // end of the board
	Point pivotDrive=new Point(); //  end of the pivot drive 
	
	BoardData(
		double _pivotX, 
		double _pivotY, 
		double _pivotDriveLength,  // negative value for negative drive
		double _pivotDriveAngle,
		double _boardDriveLength,
		double _boardLength
		)
		{
			pivot = new Point(_pivotX,_pivotY);
			pivotDriveLength = _pivotDriveLength;
			pivotDriveAngle = _pivotDriveAngle;
			boardDriveLength = _boardDriveLength;
			boardLength = _boardLength;
		}
	
	void run(Point boardStart,double a) {
		boardS.x=boardStart.x;
		boardS.y=boardStart.y;
		angle=a;
		double anglePivotDrive=angle+pivotDriveAngle;
		pivotDrive.set(
			pivot.x+pivotDriveLength*Math.sin(anglePivotDrive),
			pivot.y-pivotDriveLength*Math.cos(anglePivotDrive));
		double anglePD= Geo.angleToLowPivot(pivotDrive,boardDriveLength,boardS,boardLength);
		boardE.set(boardS.x+boardLength*Math.cos(anglePD), boardS.y+boardLength*Math.sin(anglePD));
	}
}
