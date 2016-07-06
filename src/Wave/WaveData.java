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

	
	double syncX=60;
	double syncY=driveY;
	double syncRadius=30;
	double syncPusherLength=60;
	
	double boardFix_X=5;		// point of the fixed point of the first board
	double boardFix_Y=55;
	
	public double boardData[]={
		// DriverAxis X, DriverAxis Y, DriveBarLength, DriveBarPosition, PusherLength, boardLength			
				64,	syncY,	20,				0.5*Math.PI,		74.50,				60
			 , 124,	syncY,	20,				0.5*Math.PI,		74.43,				60
			 , 180,	syncY,	20,				1.5*Math.PI,		75.39,				60
			 , 240,	syncY,	20,				1.5*Math.PI,		75.43,				60
			 , 297,	syncY,	20,				0.5*Math.PI,		74.37,				60
			};
	
	public int nofBoard=boardData.length/6;
	
	public Point crankP1= new Point();
	public Point crankP2= new Point();
	public Point syncCenter= new Point(syncX,syncY);
	public Point syncP1= new Point();
	public Point syncP2= new Point();
	
	public double angleSync1=0;
	public double angleSync2=0;
	
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
			int nofProfilePoints=0;
			if(i>0)
				nofProfilePoints=50;
			boards.add(new BoardData(boardData[p],boardData[p+1],boardData[p+2],boardData[p+3],boardData[p+4],boardData[p+5],nofProfilePoints));
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
		angleSync1=Geo.angleToRightPivot(crankP1, syncPusherLength, syncCenter, syncRadius);
		
		syncP1.set(
				syncCenter.x+syncRadius*Math.sin(angleSync1), 
				syncCenter.y-syncRadius*Math.cos(angleSync1));
		
		// find the angle of sync pivot 2
		angleSync2=Geo.angleToRightPivot(crankP2, syncPusherLength, syncCenter, syncRadius);
		
		syncP2.set(syncCenter.x+syncRadius*Math.sin(angleSync2), 
				syncCenter.y-syncRadius*Math.cos(angleSync2));
		
		Point boardStart=boardFix;
		for(int i=0;i<nofBoardToRun;i++) {
	    	BoardData bd = boards.get(i);
	    	double a;
	    	if ( (i%2)==0) 
	    		a=angleSync1;
	    	else
	    		a=angleSync2;
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
		System.out.printf("pivotP1=%s\n",syncP1);
		System.out.printf("pivotP2=%s\n",syncP2);
		System.out.printf("distance 1=%f\n",crankP1.distance(syncP1));
		System.out.printf("distance 2=%f\n",crankP2.distance(syncP2));
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
