package Wave;

import java.util.ArrayList;
import java.util.List;

import Physics.Point;

public class WaveData {
	double bottomHeight=0;

	double crankHeight=100;
	double driveY=130;
	
	Point crankCenter= new Point(0,crankHeight);
	double crankRadius=20;

	double pivotDriveLength=60;
	
	double syncX=60;
	double syncY=driveY;
	double pivotSyncRadius=30;
	
	double boardFix_X=5;		// point of the fixed point of the first board
	double boardFix_Y=55;
	
	public double boardData[]={
		// DriverAxis X, DriverAxis Y, DriveBarLength, DriveBarPosition, PusherLength, boardLength			
				70,	syncY,	20,				0.5*Math.PI,		75.90,				60
			 , 130,	syncY,	20,				0.5*Math.PI,		75.81,				60
			 , 190,	syncY,	20,				1.5*Math.PI,		74.05,				60
			 , 250,	syncY,	20,				1.5*Math.PI,		74.09,				60
			 , 310,	syncY,	20,				0.5*Math.PI,		77.87,				60
			};
	
	public int nofBoard=boardData.length/6;
	
	public Point crankP1= new Point();
	public Point crankP2= new Point();
	public Point pivotCenter= new Point(syncX,syncY);
	public Point pivotP1= new Point();
	public Point pivotP2= new Point();
	
	public double anglePivot1=0;
	public double anglePivot2=0;
	
	public double crankAngle=0;
	public double step=0.01*Math.PI;
	public double maxAngle=2*Math.PI;
	public double angle90=Math.PI/2;
	
	
	public Point boardFix= new Point(boardFix_X,boardFix_Y);
	public double boardLength1=60;
	public double boardDriveLength1=70;
	
	
	
	
	public List <BoardData> boards= new ArrayList<BoardData>();
	
	public WaveData() {
		int p=0;
		for (int i=0;i<nofBoard;i++) {
			boards.add(new BoardData(boardData[p],boardData[p+1],boardData[p+2],boardData[p+3],boardData[p+4],boardData[p+5]));
			p+=6;
		}
		start();
	}
	
	public void start()
	{
		crankAngle=0;
		run();
	}

	public void run() {
		run(nofBoard);
	}
	
	public void run(int nofBoardToRun) {
		// locate the point of crank 1
		crankP1.set(
				crankCenter.x + crankRadius*Math.cos(crankAngle),
				crankCenter.y + crankRadius*Math.sin(crankAngle));
		
		// locate the point of crank 2
		crankP2.set(
				crankCenter.x +crankRadius*Math.cos(crankAngle+angle90),
				crankCenter.y + crankRadius*Math.sin(crankAngle+angle90));
		
		// find the angle of sync pivot 1
		anglePivot1=Geo.angleToRightPivot(crankP1, pivotDriveLength, pivotCenter, pivotSyncRadius);
		
		pivotP1.set(
				pivotCenter.x+pivotSyncRadius*Math.sin(anglePivot1), 
				pivotCenter.y-pivotSyncRadius*Math.cos(anglePivot1));
		
		// find the angle of sync pivot 2
		anglePivot2=Geo.angleToRightPivot(crankP2, pivotDriveLength, pivotCenter, pivotSyncRadius);
		
		pivotP2.set(pivotCenter.x+pivotSyncRadius*Math.sin(anglePivot2), 
				pivotCenter.y-pivotSyncRadius*Math.cos(anglePivot2));
		
		Point boardStart=boardFix;
		for(int i=0;i<nofBoardToRun;i++) {
	    	BoardData bd = boards.get(i);
	    	double a;
	    	if ( (i%2)==0) 
	    		a=anglePivot1;
	    	else
	    		a=anglePivot2;
	    	bd.run(boardStart,a);
	    	boardStart=bd.boardEnd;
		}
//		node1.set(node0.x+boardLength1,pivotP1.y-boardDriveLength1);
		
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
	
	void test1()
	{
		System.out.printf("crankP1=%s\n",crankP1);
		System.out.printf("crankP2=%s\n",crankP2);
		System.out.printf("pivotP1=%s\n",pivotP1);
		System.out.printf("pivotP2=%s\n",pivotP2);
		System.out.printf("distance 1=%f\n",crankP1.distance(pivotP1));
		System.out.printf("distance 2=%f\n",crankP2.distance(pivotP2));
		BoardData bd = boards.get(0);
		System.out.printf(" board pivot = %s\n",bd.driverAxis);
		System.out.printf(" board pivotDrive = %s\n",bd.driverEnd);
		System.out.printf(" board start = %s\n",bd.boardStart);
		System.out.printf(" board end = %s\n",bd.boardEnd);
	}
	 
	public static void _main(String[] args) {
		WaveData wd= new WaveData();
		wd.test1();
	}
}
