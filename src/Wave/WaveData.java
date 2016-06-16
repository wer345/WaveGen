package Wave;

import java.util.ArrayList;
import java.util.List;

import Physics.Point;

public class WaveData {
	double bottomHeight=0;

	double driveHeight=100;
	double pivotCenterY=130;
	
	Point crankCenter= new Point(0,driveHeight);
	double crankRadius=20;

	double pivotDriveLength=60;
	
	double pivotSyncX=60;
	double pivotSyncY=pivotCenterY;
	double pivotSyncRadius=30;
	
	
	int nofBoard=2;
	double boardData[]={
		// Pivot X, Pivot Y, pivotDriveLength, pivotDriveAngle, boardDriveLength, boardLength			
				70,	pivotSyncY,	20,				0.5*Math.PI,		75,				60
			 , 130,	pivotSyncY,	20,				0.5*Math.PI,		75,				60
			};
	
	Point crankP1= new Point();
	Point crankP2= new Point();
	Point pivotCenter= new Point(pivotSyncX,pivotSyncY);
	Point pivotP1= new Point();
	Point pivotP2= new Point();
	
	double anglePivot1=0;
	double anglePivot2=0;
	
	double crankAngle=0;
	double step=0.01*Math.PI;
	double maxAngle=2*Math.PI;
	double angle90=Math.PI/2;
	
	double boardFix_X=5;
	double boardFix_Y=55;
	
	Point boardFix= new Point(boardFix_X,boardFix_Y);
//	Point node1= new Point();
	double boardLength1=60;
	double boardDriveLength1=70;
	
	
	
	
	List <BoardData> boards= new ArrayList<BoardData>();
	
	WaveData() {
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
		for(int i=0;i<nofBoard;i++) {
	    	BoardData bd = boards.get(i);
	    	double a;
	    	if ( (i%2)==0) 
	    		a=anglePivot1;
	    	else
	    		a=anglePivot2;
	    	bd.run(boardStart,a);
	    	boardStart=bd.boardE;
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
		System.out.printf(" board pivot = %s\n",bd.pivot);
		System.out.printf(" board pivotDrive = %s\n",bd.pivotDrive);
		System.out.printf(" board start = %s\n",bd.boardS);
		System.out.printf(" board end = %s\n",bd.boardE);
	}
	 
	public static void _main(String[] args) {
		WaveData wd= new WaveData();
		wd.test1();
	}
}
