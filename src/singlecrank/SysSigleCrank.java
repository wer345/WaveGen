package singlecrank;

import java.awt.Color;
import java.util.ArrayList;

import data.Equations;
import physics.Joint;
import physics.JointPush;
import physics.Obj;
import physics.Point;
import view.UI;
import view.VBase;
import view.VJoint;
import view.VJointPush;
import view.VLine;
import wave.Geo;

public class SysSigleCrank extends Obj {
	
	double r_crank;
	double x_crankCenter=0;
	double y_crankCenter=100;
	
	public Point fixP1=new Point(40,0);
	public Point freeP=new Point(0,20);
	public Point jointP1=new Point(40,20);
	public Point pushP1=new Point(35,-20);
	
//    Joint joint = new Joint(fixP,freeP,jointP);
	public JointPush jointPush = new JointPush(fixP1,freeP,jointP1,pushP1);

	public Point fixP2=new Point(-30,0);
	public Point jointP2=new Point(-30,20);
	public Joint joint2 = new Joint(fixP2,freeP,jointP2);
    
	SysSigleCrank(){
	    joint2.side=Joint.Right;
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
			double l2_fix=eq.getValue("LD_Sync2Drive");
			double a_2=eq.getValue("A_Sync2");

			double x_sync1=0.707*(l1_free+r_Sync);
			double y_sync1=0.707*(l1_free-r_Sync);
			
			double x_sync2=0.707*(l2_free+r_Sync);
			double y_sync2=0.707*(l2_free-r_Sync);
			
			freeP=new Point(x_crankCenter+r_crank,y_crankCenter);
			
			fixP1=new Point(x_crankCenter-x_sync1,y_crankCenter+y_sync1);
			jointP1=new Point();
			// get the location of jointP1 on the right side
			Geo.getJoint(null,jointP1,  freeP, fixP1, l1_free, l1_fix);
			
			pushP1=new Point(fixP1.x,fixP1.y-20);
			
			jointPush = new JointPush(fixP1,freeP,jointP1,pushP1);
			jointPush.side=Joint.Right;

			fixP2=new Point(x_crankCenter+x_sync2,y_crankCenter+y_sync2);
			jointP2=new Point();
			// get the location of jointP2 on the left side
			Geo.getJoint(jointP2, null, freeP, fixP2, l1_free, l1_fix);
			
			joint2 = new Joint(fixP2,freeP,jointP2);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	public void update(double x,double y) {
		freeP.x=x+x_crankCenter;
		freeP.y=y+y_crankCenter;
	}
	
	public static void main(String[] args) {
		SysSigleCrank sys=new SysSigleCrank();
		System.out.printf("R rank is  %f\n", sys.r_crank);

	}
	
}
