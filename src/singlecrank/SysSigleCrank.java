package singlecrank;

import data.Equations;
import physics.ContactBoard;
import physics.Joint;
import physics.JointPush;
import physics.Link;
import physics.Obj;
import physics.Point;
import wave.Angle;
import wave.Geo;

public class SysSigleCrank extends Obj {
	
	double r_crank;
	double x_crankCenter=0;
	double y_crankCenter=100;
	
//	public Point pCrank;
	public Link crank;
	
	public JointPush jSync1;
	public JointPush jSync2;

	public int nofBoard=5;
    public JointPush [] swings=new JointPush[nofBoard];
    
	public	Point boardFix;
	
	boolean hasComp=false;  // define if has a compensation board
	public	ContactBoard compBoard;
	
	public	ContactBoard [] boards= new ContactBoard[nofBoard];
    
	public	SysSigleCrank(){
		Equations eq= new Equations();
		eq.Load("data\\eqSide.txt");
		try {
			
			r_crank=eq.getValue("R_Crank");
			double l_push=eq.getValue("R_Sync");
			double r_Sync=eq.getValue("R_Crank");
			double l1_free=eq.getValue("L_Sync1Drive");
			double l1_fix=eq.getValue("LD_Sync1Drive");
			double a_1=eq.getValue("A_Sync1");

			double l2_free=eq.getValue("L_Sync2Drive");
//			double l2_fix=eq.getValue("LD_Sync2Drive");
			double a_2=eq.getValue("A_Sync2");

			double x_sync1=0.707*(l1_free+r_Sync);
			double y_sync1=0.707*(l1_free-r_Sync);
			
			double x_sync2=0.707*(l2_free+r_Sync);
			double y_sync2=0.707*(l2_free-r_Sync);
			
			crank = new Link(new Point(x_crankCenter,y_crankCenter),r_crank,0);
			//pCrank=new Point(x_crankCenter+r_crank,y_crankCenter);
			//pCrank=crank.free;
			addObj(crank);
			
			Point pFixSync1=new Point(x_crankCenter-x_sync1,y_crankCenter+y_sync1);
			Point pJointSync1=new Point();
			// get the location of jointP1 on the right side
			Geo.getJoint(null,pJointSync1,  crank.free, pFixSync1, l1_free, l1_fix);
			
			jSync1 = new JointPush(pFixSync1,crank.free,pJointSync1);
			jSync1.side=Joint.Right;
			jSync1.a_push=Angle.d2r(a_1);
			jSync1.l_push=l_push;

			Point pFixSync2=new Point(x_crankCenter+x_sync2,y_crankCenter+y_sync2);
			Point pJointSync2=new Point();
			// get the location of jointP2 on the left side
			Geo.getJoint(pJointSync2, null, crank.free, pFixSync2, l1_free, l1_fix);
			
			jSync2 = new JointPush(pFixSync2,crank.free,pJointSync2);
			jSync2.a_push=Angle.d2r(a_2);
			jSync2.l_push=l_push;
			
			addObj(jSync1);
			addObj(jSync2);
			
			double xcList[]=new double[nofBoard];
			xcList[0]=eq.getValue("XC_Push1");
			for(int i=1;i<nofBoard;i++) {
				double lastBoardLength=eq.getValue("D_Push"+i);
				xcList[i]=xcList[i-1]+lastBoardLength;
			}
			
			for(int i=0;i<nofBoard;i++) {
				double xc=xcList[i];
				Point fix=new Point(xc,jSync1.fix.y);
				JointPush jsync;
				if(i%2==1)
					jsync=jSync1;
				else
					jsync=jSync2;
				double l_drive=xc - jsync.fix.x;
				double a=-Angle.d2r(eq.getValue("A_Push"+(i+1)));
				double l_swingpush=eq.getValue("R_Push"+(i+1));
				swings[i]=new JointPush(fix, jsync.push, l_drive, l_push, Joint.Right,a,l_swingpush);
				addObj(swings[i]);
			}
			
			
			double boardFix_X;		// point of the fixed point of the first board
			double boardFix_Y;
			if(hasComp) {
				boardFix_X=-40;
				boardFix_Y=50;
			}else {
				boardFix_X=55;
				boardFix_Y=50;
			} 
			
			boardFix= new Point(boardFix_X,boardFix_Y);
			
			if(hasComp) {
				compBoard=new ContactBoard(boardFix, crank.free,50,40,Joint.Left);
				addObj(compBoard);
			}
			for (int i=0;i<nofBoard;i++) {  // nofBoard
				Point fix;
				if(i==0) {
					if(hasComp)
						fix=compBoard.joint;
					else
						fix=boardFix;
				}
				else
					fix=boards[i-1].joint;
				double l_board=eq.getValue("L_Board"+(i+1));
				double l_drive=eq.getValue("L_BdDr"+(i+1));
				boards[i]=new ContactBoard(fix,swings[i].push,l_drive,l_board,Joint.Left);
				addObj(boards[i]);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
//	public void moveTo(double x,double y) {
//		crank.free.x=x+x_crankCenter;
//		crank.free.y=y+y_crankCenter;
//	}

	public void rotate(double angle) {
		crank.angle=angle;
		update();
	}
	
	public static void _main(String[] args) {
		SysSigleCrank sys=new SysSigleCrank();
		System.out.printf("R rank is  %f\n", sys.r_crank);

	}
	
}
