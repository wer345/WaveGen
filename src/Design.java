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
			return String.format("%6.1f,%6.2f,%6.2f",Angle.r2d(crankAngle),x,y);
		}
		
	}

	
	public JointPosition getLowJoint(int resolution,int depth) {
		BoardData bd = boards.get(depth-1);
		double step=2*Math.PI/resolution;
		JointPosition jMin=new JointPosition(0,0,0);
		//double Amin=0,Amax=0,Xmin=0,Xmax=0,Ymin=0,Ymax=0;
		for(int a=0;a<=resolution;a++) {
			crankAngle=step*a;
			run(depth);
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

	/**
	 * find the length of pushers that let all joint's low position
	 * @param lowPosition
	 * @return
	 */
	public double [] getPusherLengths(double lowPosition,int resolution) {
		int size=5;
		double stepMin=0.005;
		double[] lengths= new double[size];

		for(int i=0;i<size;i++) {
			BoardData b=boards.get(i);
			double length=b.pusherLength;
			double step=0.1*length;
			int depth=i+1;
			JointPosition pos=getLowJoint(resolution,depth);
			double y=pos.y;
			int lastChange=0; // this value is 1 if the length of pusher increase in the last change
							// is -1 if the length of pusher decrease in the last change.
			while (step>stepMin) {
				if(y>lowPosition) {
					// y is above the target, increase the length
					if(lastChange==-1)
						step=0.5*step;
					b.pusherLength+=step;
					lastChange=1;
				}
				if(y<lowPosition) {
					
					if(lastChange==1)
						step=0.5*step;
					b.pusherLength-=step;
					lastChange=-1;
				}
				pos=getLowJoint(resolution,depth);
				y=pos.y;	
			}
			lengths[i]=b.pusherLength;
		}
		
		return lengths;
	}
	
	void showLowjoints()
	{
		JointPosition lastJoint=null;
		for (int depth=1;depth<=5;depth++) {
			JointPosition jmin=getLowJoint(360, depth);
			System.out.printf(" min = %s",jmin);
			if(lastJoint!=null) {
				double da=Angle.r2d(lastJoint.crankAngle-jmin.crankAngle);
				if(da<0)
					da+=360;
				System.out.printf("  diff = %6.1f,%6.2f,%6.2f\n",da,jmin.x-lastJoint.x,jmin.y-lastJoint.y);
			}
			else 
				System.out.printf("\n");
			lastJoint=jmin;
		}
	}
	 
	public static void main(String[] args) {
		Design wd= new Design();
		double [] lengths=wd.getPusherLengths(43.0,360);
		for (int i=0;i<lengths.length;i++)
			System.out.printf("pusher %d length = %6.2f\n",i,lengths[i]);
		wd.showLowjoints();
	}

}
