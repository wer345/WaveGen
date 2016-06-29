package Wave;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Physics.Point;
import Physics.Polyline;
import Physics.Vector;

public class BoardData {
	public double driverBarLength;		// length of pivot drive
	public double driverBarPositionAngle;	// the angle between the syn bar and the pivot drive
	public double pusherLength;			// the length of the drive from pivot to board
	public double boardLength;			// the length of board
	public int nofProfilePoint; 		// the number of points that are between the 2 ends of board that give the profile of board
	public double [] profileHeights;		// the heights for profile point to the board line 
	
	public Point driverAxis;				// the point the board rotate around
	public double driverAngle; 				// angle of the sync bar
	public Point driverEnd=new Point(); //  end of the pivot drive 
	public Point boardStart=new Point(); 	// start of the board
	public Point boardEnd=new Point(); 	// end of the board
	public Polyline profile;
	// DriverAxis X, DriverAxis Y, DriveBarLength, DriveBarPosition, PusherLength, boardLength			
	
	BoardData(
		double _driverAxisX, 
		double _driverAxisY, 
		double _driverBarLength,  // negative value for negative drive
		double _driverBarPositionAngle,
		double _pusherLength,
		double _boardLength,
		int _nofProfilePoint
		)
		{
			driverAxis = new Point(_driverAxisX,_driverAxisY);
			driverBarLength = _driverBarLength;
			driverBarPositionAngle = _driverBarPositionAngle;
			pusherLength = _pusherLength;
			boardLength = _boardLength;
			nofProfilePoint=_nofProfilePoint;
			setSinProfile(5);
			profile = new Polyline(nofProfilePoint+2);
		}
	
		

	/** set board height with a sin profile for test purpose
	 * @param maxHeight
	 */
	void setSinProfile(double maxHeight) {
		int sections=nofProfilePoint+1;
		double step=Math.PI/sections;
		profileHeights = new double[nofProfilePoint];
		double angle=step;
		for(int i=1;i<=nofProfilePoint;i++) {
			profileHeights[i-1]=maxHeight*Math.sin(angle);
			angle+=step;
		}
	}
	
	
	public void setProfileHeight(double [] profileHeights) {
		this.profileHeights = new double[profileHeights.length];
		for(int i=0;i<profileHeights.length;i++) {
			this.profileHeights[i]=profileHeights[i];
		}
		if(nofProfilePoint!=profileHeights.length) {
			nofProfilePoint=profileHeights.length;
			profile = new Polyline(nofProfilePoint+2);
		}
	}
	
	public void loadProfileHeight(String fileName) {
		Scanner scan;
	    File file = new File(fileName);
	    List <Double> xs= new ArrayList<Double>();
	    List <Double> ys= new ArrayList<Double>();
	    try {
	        scan = new Scanner(file);

	        while(scan.hasNextDouble())
	        {
	        	xs.add(scan.nextDouble());
	        	ys.add(-scan.nextDouble());
	        	scan.nextDouble();
	        }
	        
	    } catch (FileNotFoundException e1) {
	            e1.printStackTrace();
	    }
	    
	    // remove the first and last point
	    if(ys.size()>2) {
	    	double [] profileHeights=new double[ys.size()-2];
	    	for(int i=1;i<ys.size()-1;i++) {
	    		profileHeights[i-1]=ys.get(i);
	    	}
		    setProfileHeight(profileHeights);
		    System.out.println(profileHeights);
	    }
	    
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
		
		profile.set(0, boardStart.x, boardStart.y);
		Vector boardDirection=boardStart.vectorTo(boardEnd);
		boardDirection.normalize();
		Vector boardNormal= new Vector(boardDirection.y, -boardDirection.x);
		
		double nofSection=nofProfilePoint+1;
		double senctionLength=boardLength/nofSection;
		double distance=senctionLength;
		for(int i=1;i<=nofProfilePoint;i++) {
			Point p =profile.points.get(i);
			p.set(	boardStart.x+distance*boardDirection.x+profileHeights[i-1]*boardNormal.x,
					boardStart.y+distance*boardDirection.y+profileHeights[i-1]*boardNormal.y);
			distance+=senctionLength;
		}
		profile.set(nofProfilePoint+1, boardEnd.x, boardEnd.y);
	}
	
	// set profile height let not over the bottom line
	public void alignProfileHeight(double bottomLevel) {
		Vector boardDirection=boardStart.vectorTo(boardEnd);
		boardDirection.normalize();
		Vector boardNormal= new Vector(boardDirection.y, -boardDirection.x);
		
		double nofSection=nofProfilePoint+1;
		double senctionLength=boardLength/nofSection;
		double distance=senctionLength; // the distance from BoardStart to profile point's project of the board line
		for(int i=1;i<=nofProfilePoint;i++) {
			
			double heightToBottom= (bottomLevel-boardStart.y-distance*boardDirection.y)/boardNormal.y;
			if(profileHeights[i-1]>heightToBottom) 
				profileHeights[i-1]=heightToBottom;
			distance+=senctionLength;
		}
		
	}
}
