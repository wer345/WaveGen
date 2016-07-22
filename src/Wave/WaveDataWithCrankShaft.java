package Wave;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Physics.Line;
import Physics.Point;
import View.VLine;

public class WaveDataWithCrankShaft extends WaveBase {

	double driveY=130;
	double syncX=60;
	double syncY=driveY;
	double syncRadius=30;
	double syncPusherLength=60;
	
	double boardFix_X=-25;		// point of the fixed point of the first board
	double boardFix_Y=65;
	
	double compBoardLength=30;
	double compCrankLength=10;
	double compDriveLength=40;
	double compCrankAngle=Angle.d2r(0);
	public Point crankComp= new Point();
	
	
	public double boardData[]={
		// DriverAxis X, DriverAxis Y, DriveBarLength, DriveBarPosition, PusherLength, boardLength			
				64,	syncY,	20,				0.5*Math.PI,		74.50,				60
			 , 124,	syncY,	20,				0.5*Math.PI,		74.43,				60
			 , 180,	syncY,	20,				1.5*Math.PI,		75.39,				60
			 , 240,	syncY,	20,				1.5*Math.PI,		75.43,				60
			 , 297,	syncY,	20,				0.5*Math.PI,		74.37,				60
			};
	
	
	public Point crankP1= new Point();		
	public Point crankP2= new Point();
	public Point syncCenter= new Point(syncX,syncY);
	public Point syncP1= new Point();
	public Point syncP2= new Point();
	
	public double angleSync1=0;
	public double angleSync2=0;
	
	public double boardLength1=60;
	public double boardDriveLength1=70;
	
	
	
	public WaveDataWithCrankShaft() {
		int p=0;
		boardFix= new Point(boardFix_X,boardFix_Y);
		compBoard=new BoardData(crankCenter.x,	crankCenter.y,	compCrankLength,	0,	compDriveLength,		compBoardLength,0);
		nofBoard=boardData.length/6;
		for (int i=0;i<nofBoard;i++) {
			int nofProfilePoints=0;
			if(i>0)
				nofProfilePoints=50;
			boards.add(new BoardData(boardData[p],boardData[p+1],boardData[p+2],boardData[p+3],boardData[p+4],boardData[p+5],nofProfilePoints));
			p+=6;
		}
		start();
	}
	
	
	@Override
	public List<VLine> getLineViews()
	{
		List<VLine> lvs=new ArrayList<VLine>();
		VLine[] vs = {
				new VLine(crankCenter,crankP1,Color.blue,2), 	// crank1
				new VLine(syncCenter,syncP1,Color.blue,2), 		// pivot1
				new VLine(crankP1,syncP1,Color.blue,2),  		// pivotDrive1
				new VLine(crankCenter,crankP2,Color.green,2),  	// crank2
				new VLine(crankCenter,crankP2,Color.green,2),  	// crank2
				new VLine(syncCenter,syncP2,Color.green,2), 	// pivot2
				new VLine(crankP2,syncP2,Color.green,2),  		// pivotDrive2
				new VLine(compBoard.driverAxis,compBoard.driverEnd,Color.cyan,3),  		// compCrank
				new VLine(compBoard.driverEnd,compBoard.boardEnd,Color.cyan,3),  		// compPusher
				new VLine(compBoard.boardStart,compBoard.boardEnd,Color.cyan,3),  		// compBoard

		};
		
		for(VLine lv:vs) {
			lvs.add(lv);
		}

	    for(int idxPusher=0;idxPusher<nofBoard;idxPusher++) {
	    	BoardData bd = boards.get(idxPusher);
	    	
	    	Line board= new Line(bd.boardStart,bd.boardEnd);
		    VLine vlBoard=new VLine(board);
		    vlBoard.setColor(Color.yellow);
		    vlBoard.setSize(3);
		    lvs.add(vlBoard);
			
	    	Line pivotDrive= new Line(bd.driverAxis,bd.driverEnd);
		    VLine vlPD=new VLine(pivotDrive);
		    vlPD.setColor(Color.red);
		    vlPD.setSize(3);
		    lvs.add(vlPD);
			
	    	Line boardDrive= new Line(bd.boardEnd,bd.driverEnd);
		    VLine vlBD=new VLine(boardDrive);
		    vlBD.setColor(Color.gray);
		    vlBD.setSize(3);
		    lvs.add(vlBD);
			
	    }
		
		return lvs;
	}
	
	@Override
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
		
		compBoard.run(boardFix,crankAngle+compCrankAngle+Math.PI/2);
		
		Point boardStart=compBoard.boardEnd;
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
		WaveDataWithCrankShaft wd= new WaveDataWithCrankShaft();
		wd.test1();
	}

}
