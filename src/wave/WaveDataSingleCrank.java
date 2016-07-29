package wave;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Line;
import physics.Point;
import view.VLine;

public class WaveDataSingleCrank extends WaveBase {

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

	double sync1Drive=60; // the length of drive rod from crank tip to drive of sync
	double sync1Arm=30;	// the length of arm that is the distance from the center of sync to the drive	
	double sync1Push=20; // the length of push that is the distance from the center of sync to the push
	double sync1Angle=Angle.d2r(155); // the angle between drive and push
	
	double sync2Drive=60;
	double sync2Arm=30;
	double sync2Push=20; // the length of push that is the distance from the center of sync to the push
	double sync2Angle=Angle.d2r(155); // the angle between drive and push
	
	double DX_sync1=0.707*(sync1Drive+sync1Arm);
	double DX_sync2=0.707*(sync2Drive+sync2Arm);
	double DY_sync1=0.707*(sync1Drive-sync1Arm);
	double DY_sync2=0.707*(sync2Drive-sync2Arm);

	double sync1D2P=Geo.edgeLength(sync1Drive,sync1Push,sync1Angle);
	double sync2D2P=Geo.edgeLength(sync2Drive,sync2Push,sync2Angle);
	
	public Point crankP= new Point();		
//	public Point crankP2= new Point();
	public Point syncCenter= new Point(syncX,syncY);
	public Point syncC1= new Point(syncX,crankCenter.y+20);
	public Point syncC2= new Point(syncX,crankCenter.y-20);
	
	public Point syncD1= new Point();
	public Point syncD2= new Point();

	public Point syncP1= new Point();
	public Point syncP2= new Point();
	
	public double angleSync1=0;
	public double angleSync2=0;
	
	public double boardLength1=60;
	public double boardDriveLength1=70;
	
	public WaveDataSingleCrank() {
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
			new VLine(crankCenter,crankP,Color.blue,2), 	// crank
			
//			new VLine(syncCenter,syncP1,Color.blue,2), 		// pivot1
//			new VLine(crankP,syncP1,Color.blue,2),  		// pivotDrive1
			
			new VLine(crankP,syncD1,Color.cyan,2),  		// Sync1
			new VLine(syncD1,syncC1,Color.cyan,2),  		// Sync1
			new VLine(syncP1,syncC1,Color.cyan,2),  		// Sync1
			
			new VLine(crankP,syncD2,Color.green,2),  		// Sync2
			new VLine(syncD2,syncC2,Color.green,2),  		// Sync2
			new VLine(syncP2,syncC2,Color.green,2),  		// Sync2

			
//			new VLine(crankCenter,crankP2,Color.green,2),  	// crank2
//			new VLine(syncCenter,syncP2,Color.green,2), 	// pivot2
//			new VLine(crankP2,syncP2,Color.green,2),  		// pivotDrive2
			
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
		// set the point of crank
		crankP.set(
				crankCenter.x + crankRadius*Math.cos(crankAngle),
				crankCenter.y + crankRadius*Math.sin(crankAngle));
		
		
		// find the angle of sync pivot 1
		angleSync1=Geo.angleToRightPivot(crankP, syncPusherLength, syncCenter, syncRadius);
		
//		syncP1.set(	syncCenter.x+syncRadius*Math.sin(angleSync1), 
//					syncCenter.y-syncRadius*Math.cos(angleSync1));
//		
//		syncP2.set(	syncCenter.x+syncRadius*Math.sin(angleSync2), 
//					syncCenter.y-syncRadius*Math.cos(angleSync2));
		
		
		
		syncC1= new Point(crankCenter.x-DX_sync1,crankCenter.y+DY_sync1);
		syncC2= new Point(crankCenter.x+DX_sync2,crankCenter.y+DY_sync2);
		
		double sync1D2P=Geo.edgeLength(sync1Drive,sync1Push,sync1Angle);
		double sync2D2P=Geo.edgeLength(sync2Drive,sync2Push,sync2Angle);
		
		Geo.getJoint(null, syncD1,  crankP, syncC1, sync1Drive, sync1Arm);
		Geo.getJoint(syncP1, null,  syncD1, syncC1, sync1D2P, sync1Arm);
		
		Geo.getJoint(syncD2,null,  crankP, syncC2, sync2Drive, sync2Arm);
		Geo.getJoint(null, syncP2,  syncD2, syncC2, sync2D2P, sync1Arm);
		
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
		System.out.printf("crankP1=%s\n",crankP);
//		System.out.printf("crankP2=%s\n",crankP2);
//		System.out.printf("pivotP1=%s\n",syncP1);
//		System.out.printf("pivotP2=%s\n",syncP2);
//		System.out.printf("distance 1=%f\n",crankP.distance(syncP1));
//		System.out.printf("distance 2=%f\n",crankP2.distance(syncP2));
		BoardData bd = boards.get(0);
		System.out.printf(" board pivot = %s\n",bd.driverAxis);
		System.out.printf(" board pivotDrive = %s\n",bd.driverEnd);
		System.out.printf(" board start = %s\n",bd.boardStart);
		System.out.printf(" board end = %s\n",bd.boardEnd);
	}
	 
	public static void _main(String[] args) {
		WaveDataSingleCrank wd= new WaveDataSingleCrank();
		wd.test1();
	}

}
