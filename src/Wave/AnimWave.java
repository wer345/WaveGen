package Wave;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import Physics.Line;
import Physics.Point;
import View.UI;
import View.Range;
import View.VBase;
import View.VLine;


public class AnimWave extends UI {
	private static final long serialVersionUID = 1L;

	private int y = 0;
	WaveData d=new WaveData();
	
	Line crank1= new Line(d.crankCenter,d.crankP1);
	Line crank2= new Line(d.crankCenter,d.crankP2);
	
	Line pivot1= new Line(d.pivotCenter,d.pivotP1);
	Line pivot2= new Line(d.pivotCenter,d.pivotP2);
	
	Line pivotDrive1= new Line(d.crankP1,d.pivotP1);
	Line pivotDrive2= new Line(d.crankP2,d.pivotP2);

//	Line board1= new Line(d.node0,d.node1);
//	Line boardDrive1= new Line(d.pivotP1,d.node1);
	
	
	public void setup() {
		frametime=20;
	    //frame.setSize(800, 600);
		int pixWindowX=600;
		int pixWindowY=400;
		double originX=0.2; // x position of origin in the Window, 0 - left edge, 1 - right edge
		double originY=0.2; // y position of origin in the Window, 0 - bottom edge, 1 - top edge
		
		double physicXsize=500; // the physic size in x the windows shows.
		
		setWindow(pixWindowX,pixWindowY);
		
		double windowYXRate=pixWindowY;
		windowYXRate/=pixWindowX;
	    VBase.rangeDefault= new Range(-originX*physicXsize,-originY*physicXsize*windowYXRate,
	    		(1-originX)*physicXsize,(1-originY)*physicXsize*windowYXRate);
		

		
	    Line [] lineset1= {crank1,pivot1,pivotDrive1};
	    
	    for( Line line:lineset1) {
		    VLine vl1=new VLine(line);
		    vl1.setColor(Color.blue);
		    vl1.setSize(2);
			addView(vl1);
	    }
		
	    Line [] lineset2= {crank2,pivot2,pivotDrive2};
	    
	    for( Line line:lineset2) {
		    VLine vl2=new VLine(line);
		    vl2.setColor(Color.green);
		    vl2.setSize(3);
			addView(vl2);
	    }
	    
	    for(int i=0;i<d.nofBoard;i++) {
	    	BoardData bd = d.boards.get(i);
	    	Line board= new Line(bd.boardStart,bd.boardEnd);
		    VLine vlBoard=new VLine(board);
		    vlBoard.setColor(Color.yellow);
		    vlBoard.setSize(3);
			addView(vlBoard);

	    	Line pivotDrive= new Line(bd.driverAxis,bd.driverEnd);
		    VLine vlPD=new VLine(pivotDrive);
		    vlPD.setColor(Color.red);
		    vlPD.setSize(3);
			addView(vlPD);
			
	    	Line boardDrive= new Line(bd.boardEnd,bd.driverEnd);
		    VLine vlBD=new VLine(boardDrive);
		    vlBD.setColor(Color.gray);
		    vlBD.setSize(3);
			addView(vlBD);
			
	    }
	    
	}

	public void loop()
	{
		if(!d.next())
			d.start();
	}
	
	public static void main(String[] args) {
		start(new AnimWave());
	}
}
