package Wave;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.utils.DataTable;

import Physics.Point;
import Physics.Vector;


public class Volume extends Design{

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

		for(int i=1;i<pointList.length;i++) {
			if(pointList[i].y-bottomHeight<0.05) {
				idxLow=i;
				System.out.printf("Low point at %d\n",idxLow);
				break;
			}
		}
		
//		double yLow=pointList[0].y;
//		for(int i=1;i<pointList.length;i++) {
//			if(yLow>pointList[i].y) {
//				yLow=pointList[i].y;
//				idxLow=i;
//			}
//		}
		

		double v1=(compBoard.boardEnd.x-compBoard.boardStart.x)*(0.5*(compBoard.boardEnd.y+compBoard.boardStart.y)-bottomHeight);
		BoardData bd=boards.get(0);
		double v2=(bd.boardEnd.x-bd.boardStart.x)*(0.5*(bd.boardEnd.y+bd.boardStart.y)-bottomHeight);
		
		Point last=pointList[0];
		double v3=0;
		for(int i=1;i<=idxLow;i++) {
			v3+=(pointList[i].x-last.x)*(0.5*(pointList[i].y+last.y)-bottomHeight);
			last=pointList[i];
		}
		double v=v1+v2+3;
		System.out.printf("%6.3f,%6.3f,%6.3f\n",v1,v2,v3);
		return v;
	}
	
	
	int findLowestIndex(Point[] pointList,int start)
	{
		int size=pointList.length;
		int idx=-1;
		int p=start;
		double y0=pointList[p].y;
		double min=0.1;
		while(p<size) {
			if(y0<min+bottomHeight) {
				if(p+1<size) {
					p++;
					double y=pointList[p].y;
					if(y>y0)
						return p;
					y0=y;
				}
				return p;
			}
			else {
				if(p+1<size) {
					p++;
					y0=pointList[p].y;
				}
				else
					return -1;
			}
		}
		return idx;
	}
	
	public void findVolume() {
		setPusherLength();
		findProfile();
//	    String [] PusherDataFile={"data\\board1.txt","data\\board2.txt","data\\board3.txt","data\\board4.txt"};
//	    for(int idxPusher=1;idxPusher<nofBoard;idxPusher++) {
//	    	BoardData bd = boards.get(idxPusher);
//    		bd.loadProfileHeight(PusherDataFile[idxPusher-1]);
//	    }
//		int resolution=360;
//		double step=2*Math.PI/resolution;
//		
//		crankAngle=step*1;
//		run(nofBoard);
//		for(int i=0;i<ps.length;i++) {
//		System.out.printf("%6.2f,  %6.2f\n",ps[i].x,ps[i].y);
//	}
		
//		for(int i=1;i<nofBoard;i++) {
//			BoardData bd = boards.get(i);
//			System.out.printf("Board %d,  %s --- %s\n",i,bd.boardStart,bd.boardEnd);
//		}
		
		int resolution=360;
		double step=2*Math.PI/resolution;
		DataTable tb = new DataTable();
		tb.numberEncoded=false;
		int rowcrankAngle=20;
		int colProfile=10;
		crankAngle=step*0;
		run(nofBoard);
		Point[] ps =getWavePoints(null);
		
		
		double v;
		v=getVolume(ps,boardFix,bottomHeight);
		System.out.printf("v = %6.2f\n",v);

		ValueList vols = new ValueList();
		double last=0;
		int switchPoint=0;
		double[] temp= new double[360];
		for (int a=0;a<resolution;a++) {
			crankAngle=step*a;
			run(nofBoard);
			ps =getWavePoints(ps);
			System.out.printf("%d",a);
			//v=getVolume(ps,boardFix,bottomHeight);
			
			Point[] pointList=ps;
			Point fix=boardFix;
			int idxLow=0;

			for(int i=1;i<pointList.length;i++) {
				if(pointList[i].y-bottomHeight<0.05) {
					idxLow=i;
					System.out.printf("Low point at %d\n",idxLow);
					break;
				}
			}
			
			idxLow = findLowestIndex(pointList,0);
			if(idxLow<0) {
				System.out.printf("ERROR: Not enclosured at %d\n", a);
				break;
			}
			
			int idxLow2=-1;
			
			if(idxLow+50<pointList.length)
				idxLow2=findLowestIndex(pointList,idxLow+50);
			
//			double yLow=pointList[0].y;
//			for(int i=1;i<pointList.length;i++) {
//				if(yLow>pointList[i].y) {
//					yLow=pointList[i].y;
//					idxLow=i;
//				}
//			}
			

			double v1=(compBoard.boardEnd.x-compBoard.boardStart.x)*(0.5*(compBoard.boardEnd.y+compBoard.boardStart.y)-bottomHeight);
			BoardData bd=boards.get(0);
			double v2=(bd.boardEnd.x-bd.boardStart.x)*(0.5*(bd.boardEnd.y+bd.boardStart.y)-bottomHeight);
			
			Point lastPoint=pointList[0];
			int idxLowAll=idxLow;
//			if(idxLow2>=0)
//				idxLowAll=idxLow2;
			
			double v3=0;
			for(int i=1;i<=idxLowAll;i++) {
				v3+=(pointList[i].x-lastPoint.x)*(0.5*(pointList[i].y+lastPoint.y)-bottomHeight);
				lastPoint=pointList[i];
			}
			v=v1+v2+v3;
			
			System.out.printf("%6.3f,%6.3f,%6.3f\n",v1,v2,v3);

			
			temp[a]=v;
			if(a>0) {
				if(v-last>2000) {
					switchPoint=a;
					System.out.printf("Switch at %d\n",a);
				}
			}
			last=v;
			//System.out.printf("%d, %6.2f\n",a,v);
			//System.out.printf("%d, %6.2f\n",a,v);
			int col=0;
			int r=a+rowcrankAngle;
			tb.SetItem(r, col++, a);
			tb.SetItem(r, col++, idxLow);
			tb.SetItem(r, col++, idxLow2);
			tb.SetItem(r, col++, v1+v2);
			tb.SetItem(r, col++, v3);
			tb.SetItem(r, col++, v);

			if(a<360) {
				col=colProfile;
				for(Point p:ps) {
					tb.SetItem(r, col, p.y-bottomHeight);
					col++;
				}
			}
		}
		
		for(int a=switchPoint;a<360;a++) {
			vols.add(temp[a]);
		}
		for(int a=0;a<switchPoint;a++) {
			vols.add(temp[a]);
		}

		
//		for(Double va:vols) {
//			System.out.printf("%6.2f\n",va);
//		}
		tb.SaveToCSV("volume.csv");
	}
	
	public static void main(String[] args) {
		//getPusherLength();
		Volume vlm= new Volume();
		vlm.findVolume();
	}
	
}
