import Wave.BoardData;
import Wave.WaveData;


public class Design extends WaveData{
	class JointPosition {
		double crankAngle;
		double x,y;
		
		public JointPosition(double crankAngle, double x, double y) {
			super();
			this.crankAngle = crankAngle;
			this.x = x;
			this.y = y;
		}

		public void set(double crankAngle, double x, double y) {
			this.crankAngle = crankAngle;
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String toString() {
			return String.format("%6.1f,%6.2f,%6.2f\n",180*crankAngle/Math.PI,x,y);
		}
		
		
	}
	
	public JointPosition getLowJoint(int resolution) {
		BoardData bd = boards.get(0);
		double step=2*Math.PI/resolution;
		JointPosition jMin=new JointPosition(0,0,0);
		//double Amin=0,Amax=0,Xmin=0,Xmax=0,Ymin=0,Ymax=0;
		for(int a=0;a<=resolution;a++) {
			crankAngle=step*a;
			run(1);
			//System.out.printf(" %d %s\n",a,bd.boardEnd);
			double x=bd.boardEnd.x;
			double y=bd.boardEnd.y;
			if(a==0) {
				jMin.set(crankAngle, x, y);
			}
			else {
				if(jMin.y>y) {
					jMin.set(crankAngle, x, y);
				}	
			}
		}
		return jMin;
	}
	
	void test()
	{
		BoardData bd = boards.get(0);
		int resolution=360;
		double step=2*Math.PI/resolution;
		double Amin=0,Amax=0,Xmin=0,Xmax=0,Ymin=0,Ymax=0;
		for(int a=0;a<=resolution;a++) {
			crankAngle=step*a;
			run(1);
			System.out.printf(" %d %s\n",a,bd.boardEnd);
			double x=bd.boardEnd.x;
			double y=bd.boardEnd.y;
			if(a==0) {
				Amin=Amax=a;
				Xmin=Xmax=x;
				Ymin=Ymax=y;
			}
			else {
				if(Ymin>y) {
					Amin=a;
					Xmin=x;
					Ymin=y;
				} else if(Ymax<y){
					Amax=a;
					Xmax=x;
					Ymax=y;
				}
					
			}
		}
		System.out.printf(" min = %6.1f,%6.2f,%6.2f\n",Amin,Xmin,Ymin);
		System.out.printf(" max = %6.1f,%6.2f,%6.2f\n",Amax,Xmax,Ymax);
	}
	 
	public static void main(String[] args) {
		Design wd= new Design();
		
		JointPosition jmin=wd.getLowJoint(360);
		System.out.printf(" min = %s\n",jmin);
		//wd.test();
	}

}
