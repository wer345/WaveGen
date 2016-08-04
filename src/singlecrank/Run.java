package singlecrank;

import physics.ContactBoard;
import physics.JointPosition;
import physics.Point;
import wave.Angle;

import com.utils.DataTable;

public class Run {
	SysSigleCrank sys;
	
	Run(SysSigleCrank sys) {
		this.sys=sys;
	}

	int saveTitle(DataTable tb) {
		int row=0;
		
		for(int ib=0;ib<sys.nofBoard;ib++) {
			ContactBoard b=sys.boards[ib];
			row=0;
			int col=2*ib+1;
			tb.setItem(row++,col,"Board "+(ib+1));
			tb.setItem(row,0,"Drive length");
			tb.setItem(row,col,b.p);
			row++;
			tb.setItem(row,0,"Board length");
			tb.setItem(row,col,b.q);
			row++;
		}
		return row;
	}
	
	void saveNodes(int resolution) {
		DataTable tb = new DataTable();
		
		int row=saveTitle(tb);
		
		for(int ib=0;ib<sys.nofBoard;ib++) {
			ContactBoard b=sys.boards[ib];
			int col=2*ib+1;
			tb.setItems(row,col,"X","Y");
		}
		
		row++;
		double step=2*Math.PI/resolution;
		
		double angle=0;
		for(int ia=0;ia<resolution;ia++) {
			sys.rotate(angle);
			tb.setItem(row, 0, ia);
			for(int ib=0;ib<sys.nofBoard;ib++) {
				ContactBoard b=sys.boards[ib];
				int col=2*ib+1;
				tb.setItem(row,col,b.joint.x);
				tb.setItem(row,col+1,b.joint.y);
			}
			row++;
			angle+=step;
		}
		
		tb.SaveToCSV("Nodes.csv");
		
	}
	
	double vAngle(Point fix,Point free) {
		double a=Angle.r2d(Math.atan2(free.x-fix.x,-free.y+fix.y));
		return a;
	}
	
	void saveAngles(int resolution) {
		DataTable tb = new DataTable();
		
		int row = saveTitle(tb);
		
		tb.setItems(row,0,"Index","Crank","Sync 1","Sync 2");
		int col=4;
		for(int ib=0;ib<sys.nofBoard;ib++) {
			ContactBoard b=sys.boards[ib];
			tb.setItem(row,col++,"Drive "+(ib+1));
		}
		row++;
		
		double step=2*Math.PI/resolution;
		
		double angle=0;
		for(int ia=0;ia<resolution;ia++) {
			sys.rotate(angle);
			col=0;
			tb.setItem(row, col++, ia);
			tb.setItem(row, col++, Angle.r2d(angle));
			tb.setItem(row, col++, vAngle(sys.jSync1.fix,sys.jSync1.push));
			tb.setItem(row, col++, vAngle(sys.jSync2.fix,sys.jSync2.push));
			
//			tb.setItem(row, col++, vAngle(sys.jSync1.fix,sys.jSync1.joint)-135);
//			tb.setItem(row, col++, vAngle(sys.jSync2.fix,sys.jSync2.joint)+135);

			for(int ib=0;ib<sys.nofBoard;ib++) {
				ContactBoard b=sys.boards[ib];
				tb.setItem(row, col++, vAngle(b.free,b.joint));
			}
			row++;
			angle+=step;
		}
		
		tb.SaveToCSV("angles.csv");
		
	}
	
	//find joints that is in lowest position
	public JointPosition [] getLowJoint(int resolution) {
		JointPosition [] rst= new JointPosition [sys.nofBoard];
		
		double step=2*Math.PI/resolution;
		for(int a=0;a<=resolution;a++) {
			double crankAngle=step*a;
			sys.rotate(crankAngle);
			for(int ib=0;ib<sys.nofBoard;ib++) {
				ContactBoard bd = sys.boards[ib];
	
				double x=bd.joint.x;
				double y=bd.joint.y;
				if(a==0) {
					rst[ib] = new JointPosition(crankAngle, x, y);
				}
				else {
					if(rst[ib].y>y) {
						rst[ib].set(crankAngle, x, y);
					}	
				}
			}
		}
		return rst;
	}
	
	void showLows() {
		JointPosition [] lows= getLowJoint(360);
		for(int i=0;i<lows.length;i++) {
			JointPosition low=lows[i];
			System.out.printf("%d) %5.1f, %6.2f, %6.2f\n",i,Angle.r2d(low.crankAngle),low.x,low.y);
		}
	}
	
	static public void main(String [] args) {
		Run r=new Run(new SysSigleCrank());
		r.showLows();
		r.saveNodes(180);
		//r.saveAngles(120);
		
	}
}