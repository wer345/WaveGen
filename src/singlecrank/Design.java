package singlecrank;

import physics.ContactBoard;
import wave.Angle;


public class Design
{
	public SysSigleCrank sys;
	
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

	public Design(SysSigleCrank sys) {
		this.sys=sys;
	}
	
	//find the joint that is in lowest position
	public JointPosition getLowJoint(int resolution,int depth) {
		ContactBoard bd = sys.boards[depth-1];
		double step=2*Math.PI/resolution;
		JointPosition jMin=new JointPosition(0,0,0);
		for(int a=0;a<=resolution;a++) {
			double crankAngle=step*a;
			sys.rotate(crankAngle);
			sys.update();
		//	sys.run(depth);
			//System.out.printf(" %d %s\n",a,bd.boardEnd);
			double x=bd.joint.x;
			double y=bd.joint.y;
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
	 * @param lowPosition -- the lowest joint reaches this low position
	 * @return the length of pushers
	 */
	public double [] getPusherLengths(double lowPosition,int resolution) {
//		int size=5;
		double stepMin=0.005;
		double[] lengths= new double[sys.nofBoard];

		for(int i=0;i<sys.nofBoard;i++) {
			ContactBoard b=sys.boards[i];
			double length=b.p;
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
					b.p+=step;
					lastChange=1;
				}
				if(y<lowPosition) {
					
					if(lastChange==1)
						step=0.5*step;
					b.p-=step;
					lastChange=-1;
				}
				pos=getLowJoint(resolution,depth);
				y=pos.y;	
			}
			lengths[i]=b.p;
		}
		
		return lengths;
	}
	
//	void showLowjoints()
//	{
//		JointPosition lastJoint=null;
//		for (int depth=1;depth<=5;depth++) {
//			JointPosition jmin=getLowJoint(360, depth);
//			System.out.printf(" min = %s",jmin);
//			if(lastJoint!=null) {
//				double da=Angle.r2d(lastJoint.crankAngle-jmin.crankAngle);
//				if(da<0)
//					da+=360;
//				System.out.printf("  diff = %6.1f,%6.2f,%6.2f\n",da,jmin.x-lastJoint.x,jmin.y-lastJoint.y);
//			}
//			else 
//				System.out.printf("\n");
//			lastJoint=jmin;
//		}
//	}

	/**
	 * Set all pusher's length to let all low joint  reach the bottom line
	 */
	
//	void setPusherLength() {
////		Design wd= new Design();
//		double [] lengths=getPusherLengths(bottomHeight,360);
//		for (int i=0;i<lengths.length;i++)
//			System.out.printf("pusher %d length = %6.2f\n",i,lengths[i]);
//		showLowjoints();
//	}
	
//	public void findProfile() {
//		int resolution=360;
//		double step=2*Math.PI/resolution;
//		JointPosition jMin=new JointPosition(0,0,0);
//		for(int i=1;i<nofBoard;i++) {
//			BoardData bd = boards.get(i);
//			int npp=bd.nofProfilePoint;
//			for(int j=0;j<npp;j++) 
//				bd.profileHeights[j]=100000;
//
////			System.out.printf("board %d, # of profile points  %d\n",i,npp);
////			for(int j=0;j<npp;j++) {
////				System.out.printf("point %d, profile height=  %6.2f\n",j,bd.profileHeights[j]);
////			}
//		}
//		
//		for(int a=0;a<=resolution;a++) {
//			crankAngle=step*a;
//			run(nofBoard);
//			for(int i=1;i<nofBoard;i++) {
//				BoardData bd = boards.get(i);
//				bd.alignProfileHeight(bottomHeight);
//			}
//		}
//	}

//	public void showProfile() {
//		for(int i=1;i<nofBoard;i++) {
//			BoardData bd = boards.get(i);
//			int npp=bd.nofProfilePoint;
//			double secLength=bd.boardLength/(bd.nofProfilePoint+1);
//			System.out.printf("\nboard %d, # of profile points  %d\n",i,npp);
//			
//			System.out.printf("    0.0,    0.0,    0.0\n");
//			for(int j=0;j<npp;j++) {
//				double x=secLength*(1+j);
//				System.out.printf("%6.2f, %6.2f,  0.0\n",x, -bd.profileHeights[j]);
//			}
//			System.out.printf("%6.2f, %6.2f,  0.0\n",bd.boardLength,0.0);
//		}
//	}
	
	public static void main(String[] args) {
		
		SysSigleCrank sys = new SysSigleCrank();
		Design wd= new Design(sys);
		double [] lengths = wd.getPusherLengths(40,360);
		//wd.findProfile();
		//wd.showProfile();
	}
}
