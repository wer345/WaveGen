package singlecrank;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import physics.ContactBoard;
import physics.Point;
import physics.Vector;
import wave.ValueList;

import com.utils.DataTable;


public class SingleVolume {
	SysSigleCrank sys;
	SingleDesign design;
	
	SingleVolume(SysSigleCrank sys) {
		this.sys=sys;
		design = new SingleDesign(sys);
	}
	
	public Point[] getWavePoints(Point[] pointList) {
		Point[] ps=pointList;
		if(ps==null) {
			int nofPoints=0;
			for(int i=1;i<sys.nofBoard;i++) {
		    	ContactBoard bd = sys.boards[i];
				nofPoints += bd.profile.points.size()-1;
			}
			ps=new Point[nofPoints];
			for(int i=0;i<nofPoints;i++) 
				ps[i]=new Point(0,0);
		}
		
		int idxPoint=0;
		for(int i=1;i<sys.nofBoard;i++) {
	    	ContactBoard bd = sys.boards[i];
	    	int n=bd.profile.points.size()-1;
			for(int idxpp=0;idxpp<n;idxpp++) {
				ps[idxPoint++].set(bd.profile.points.get(idxpp));
			}
		}
		
		return ps;
	}

//	public double getVolume(Point[] pointList,Point fix,double bottomHeight) {
//		int idxLow=0;
//
//		for(int i=1;i<pointList.length;i++) {
//			if(pointList[i].y-bottomHeight<0.05) {
//				idxLow=i;
//				System.out.printf("Low point at %d\n",idxLow);
//				break;
//			}
//		}
//		
////		double yLow=pointList[0].y;
////		for(int i=1;i<pointList.length;i++) {
////			if(yLow>pointList[i].y) {
////				yLow=pointList[i].y;
////				idxLow=i;
////			}
////		}
//		
//
//		double v1=0;
//		if(sys.hasComp) {
//			v1=(sys.compBoard.joint.x-sys.compBoard.fix.x)*(0.5*(sys.compBoard.joint.y+sys.compBoard.fix.y)-bottomHeight);
//		}
//		ContactBoard bd=sys.boards[0];
//		double v2=(bd.joint.x-bd.fix.x)*(0.5*(bd.joint.y+bd.fix.y)-bottomHeight);
//		
//		Point last=pointList[0];
//		double v3=0;
//		for(int i=1;i<=idxLow;i++) {
//			v3+=(pointList[i].x-last.x)*(0.5*(pointList[i].y+last.y)-bottomHeight);
//			last=pointList[i];
//		}
//		double v=v1+v2+3;
//		System.out.printf("%6.3f,%6.3f,%6.3f\n",v1,v2,v3);
//		return v;
//	}
	
	
	int findLowestIndex(Point[] pointList,int start)
	{
		int size=pointList.length;
		int idx=-1;
		int p=start;
		double y0=pointList[p].y;
		double min=0.1;
		while(p<size) {
			if(y0<min+sys.bottomHeight) {
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
		// setPusherLength();
		design.findProfile();
		
		int resolution=360;
		double step=2*Math.PI/resolution;
		
		DataTable tb = new DataTable();
		
		tb.numberEncoded=false;
		int rowcrankAngle=10;   // the row index to show first angle data
		int colProfile=10;		// the col index to show profile data
		double crankAngle=step*0;
		sys.rotate(crankAngle);
		Point[] ps =getWavePoints(null);
		
		
		double volAll;
//		v=getVolume(ps,sys.boardFix,sys.bottomHeight);
//		System.out.printf("v = %6.2f\n",v);

		int iRow = rowcrankAngle-1;
		tb.setItems(iRow, 0, "Angle index","Low 1","Low 2","V Comp+Guide", "V Profile", "V All"); 
		for(int i=0;i<ps.length;i++) {
			tb.setItem(iRow, colProfile+i,i+1);
		}
		
		ValueList lstVolAll = new ValueList();
		ValueList lstVolGuide = new ValueList();
		ValueList lstVolProfile = new ValueList();
		double last=0;
		int switchPoint=0;
		double[] aryVolGuide= new double[resolution];
		double[] aryVolProfile= new double[resolution];
		double[] aryVolAll= new double[resolution];
		for (int iAngle=0;iAngle<resolution;iAngle++) {
			crankAngle=step*iAngle;
			sys.rotate(crankAngle);
			ps =getWavePoints(ps);
			//System.out.printf("%d",iAngle);
			
			//v=getVolume(ps,boardFix,bottomHeight);
			
			Point[] pointList=ps;
//			Point fix=sys.boardFix;
			int idxLow=0;

			for(int i=1;i<pointList.length;i++) {
				if(pointList[i].y-sys.bottomHeight<0.05) {
					idxLow=i;
					//System.out.printf("Low point at %d\n",idxLow);
					break;
				}
			}
			
			idxLow = findLowestIndex(pointList,0);
			if(idxLow<0) {
				System.out.printf("ERROR: Not enclosured at %d\n", iAngle);
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
			

			double volComp=0;
			double volGuide=0;
			//v2=(bd.boardEnd.x-bd.boardStart.x)*(0.5*(bd.boardEnd.y+bd.boardStart.y)-bottomHeight);
			if(sys.hasComp) {
				volComp=(sys.compBoard.joint.x-sys.compBoard.fix.x)*(0.5*(sys.compBoard.joint.y+sys.compBoard.fix.y)-sys.bottomHeight);
			}
			
			ContactBoard bd=sys.boards[0];
			volGuide=(bd.joint.x-bd.fix.x)*(0.5*(bd.joint.y+bd.fix.y)-sys.bottomHeight);
			
			Point lastPoint=pointList[0];
			int idxLowAll=idxLow;
//			if(idxLow2>=0)
//				idxLowAll=idxLow2;
			
			double volProfile=0;
			for(int i=1;i<=idxLowAll;i++) {
				volProfile+=(pointList[i].x-lastPoint.x)*(0.5*(pointList[i].y+lastPoint.y)-sys.bottomHeight);
				lastPoint=pointList[i];
			}
			volAll=volComp+volGuide+volProfile;
			aryVolGuide[iAngle]=volComp+volGuide;
			aryVolProfile[iAngle]=volProfile;
			aryVolAll[iAngle]=volAll;
			
//			System.out.printf("%6.3f,%6.3f,%6.3f\n",volComp,volGuide,volProfile);
			if(iAngle>0) {
				if(volAll-last>2000) {
					switchPoint=iAngle;
					System.out.printf("Switch at %d\n",iAngle);
				}
			}
			last=volAll;
			//System.out.printf("%d, %6.2f\n",a,v);
			//System.out.printf("%d, %6.2f\n",a,v);
			int col=0;
			iRow=iAngle+rowcrankAngle;
			tb.setItem(iRow, col++, iAngle);
			tb.setItem(iRow, col++, idxLow);
			tb.setItem(iRow, col++, idxLow2);
			tb.setItem(iRow, col++, volComp+volGuide);
			tb.setItem(iRow, col++, volProfile);
			tb.setItem(iRow, col++, volAll);

			if(iAngle<360) {
				col=colProfile;
				for(Point p:ps) {
					tb.setItem(iRow, col, p.y-sys.bottomHeight);
					col++;
				}
			}
		}

		tb.SaveToCSV("volume.csv");
		
		for(int a=switchPoint;a<resolution;a++) {
			lstVolGuide.add(aryVolGuide[a]);
			lstVolProfile.add(aryVolProfile[a]);
			lstVolAll.add(aryVolAll[a]);
		}
		for(int a=0;a<switchPoint;a++) {
			lstVolGuide.add(aryVolGuide[a]);
			lstVolProfile.add(aryVolProfile[a]);
			lstVolAll.add(aryVolAll[a]);
		}


		tb.clear();
		int row=0;
		tb.setItems(row++, 0, "index","Angle","V Guide","V profile","V all");
		for(int i=0;i<resolution;i++) {
			int col=0;
			tb.setItem(row, col++, i);
			double angle=i+switchPoint;
			if(angle>360)
				angle-=360;
			tb.setItem(row, col++, angle);
			tb.setItem(row, col++, lstVolGuide.get(i));
			tb.setItem(row, col++, lstVolProfile.get(i));
			tb.setItem(row, col++, lstVolAll.get(i));
			row++;
		}

		tb.SaveToCSV("volume_aligned.csv");
		
//		for(Double va:vols) {
//			System.out.printf("%6.2f\n",va);
//		}
		
	}
	
	public static void main(String[] args) {
		SysSigleCrank sys = new SysSigleCrank();
		//getPusherLength();
		SingleVolume vlm= new SingleVolume(sys);
		vlm.findVolume();
	}
	
}
