import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import Physics.Point;
import Physics.Vector;
import Wave.BoardData;
import Wave.WaveData;


public class Volume extends WaveData{

	public Point[] getWavePoints(Point[] pointList) {
		
		Point[] ps=pointList;
		if(ps==null) {
			int nofPoints=0;
			for(int i=1;i<nofBoard;i++) {
		    	BoardData bd = boards.get(i);
				nofPoints += bd.profile.points.size()-1;
			}
			ps=new Point[nofPoints];
			for(int i=0;i<nofPoints;i++) 
				ps[i]=new Point(0,0);
		}
		
		int idxPoint=0;
		for(int i=1;i<nofBoard;i++) {
	    	BoardData bd = boards.get(i);
	    	int n=bd.profile.points.size()-1;
			for(int idxpp=0;idxpp<n;idxpp++) {
				ps[idxPoint++].set(bd.profile.points.get(idxpp));
			}
		}
		
		return ps;
	}

	public double getVolume(Point[] pointList,Point fix,double bottomHeight) {
		int idxLow=0;
		double yLow=pointList[0].y;
		for(int i=1;i<pointList.length;i++) {
			if(yLow>pointList[i].y) {
				yLow=pointList[i].y;
				idxLow=i;
			}
		}

		Point last=fix;
		double v=0;
		for(int i=0;i<=idxLow;i++) {
			v+=(pointList[i].x-last.x)*(0.5*(pointList[i].y+last.y)-bottomHeight);
			last=pointList[i];
		}
		return v;
	}
	
	
	public void findVolume() {
	    String [] PusherDataFile={"data\\board1.txt","data\\board2.txt","data\\board3.txt","data\\board4.txt"};
	    for(int idxPusher=1;idxPusher<nofBoard;idxPusher++) {
	    	BoardData bd = boards.get(idxPusher);
    		bd.loadProfileHeight(PusherDataFile[idxPusher-1]);
	    }
		int resolution=360;
		double step=2*Math.PI/resolution;
		
		crankAngle=step*1;
		run(nofBoard);
		for(int i=1;i<nofBoard;i++) {
			BoardData bd = boards.get(i);
			System.out.printf("Board %d,  %s --- %s\n",i,bd.boardStart,bd.boardEnd);
		}
		
		Point[] ps =getWavePoints(null);
		
		for(int i=0;i<ps.length;i++) {
			System.out.printf("%6.2f,  %6.2f\n",ps[i].x,ps[i].y);
		}
		
		double v=getVolume(ps,boardFix,43);
		System.out.printf("v = %6.2f\n",v);

		for (int a=1;a<=360;a++) {
			crankAngle=step*a;
			run(nofBoard);
			ps =getWavePoints(ps);
			v=getVolume(ps,boardFix,43);
			System.out.printf("%d, %6.2f\n",a,v);
		}
		
	}
	
	public static void main(String[] args) {
		//getPusherLength();
		Volume vlm= new Volume();
		vlm.findVolume();
	}
	
}
