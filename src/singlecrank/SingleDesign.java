package singlecrank;

import physics.ContactBoard;
import physics.JointPosition;
import wave.Angle;
import wave.BoardData;


public class SingleDesign
{
	public SysSigleCrank sys;
	Run run;

	public SingleDesign(SysSigleCrank sys) {
		this.sys=sys;
		run= new Run(sys);
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
//			int depth=i+1;
			JointPosition [] lows=run.getLowJoint(resolution);
			JointPosition pos=lows[i];
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
				lows=run.getLowJoint(resolution);
				pos=lows[i];
				y=pos.y;	
			}
			lengths[i]=b.p;
		}
		
		return lengths;
	}
	

	public void findProfile() {
		int resolution=360;
		double step=2*Math.PI/resolution;
		JointPosition jMin=new JointPosition(0,0,0);
		for(int i=1;i<sys.nofBoard;i++) {
			ContactBoard bd = sys.boards[i];
			int npp=bd.nofProfilePoint;
			for(int j=0;j<npp;j++) 
				bd.profileHeights[j]=100000;

//			System.out.printf("board %d, # of profile points  %d\n",i,npp);
//			for(int j=0;j<npp;j++) {
//				System.out.printf("point %d, profile height=  %6.2f\n",j,bd.profileHeights[j]);
//			}
		}
		
		for(int a=0;a<=resolution;a++) {
			double crankAngle=step*a;
			sys.rotate(crankAngle);
			for(int i=1;i<sys.nofBoard;i++) {
				ContactBoard bd = sys.boards[i];
				bd.alignProfileHeight(sys.bottomHeight);
			}
		}
	}

	public void showProfile() {
		for(int i=1;i<sys.nofBoard;i++) {
			ContactBoard bd = sys.boards[i];
			int npp=bd.nofProfilePoint;
			double secLength=bd.boardLength/(bd.nofProfilePoint+1);
			System.out.printf("\nboard %d, # of profile points  %d\n",i,npp);
			
			System.out.printf("    0.0,    0.0,    0.0\n");
			for(int j=0;j<npp;j++) {
				double x=secLength*(1+j);
				System.out.printf("%6.2f, %6.2f,  0.0\n",x, -bd.profileHeights[j]);
			}
			System.out.printf("%6.2f, %6.2f,  0.0\n",bd.boardLength,0.0);
		}
	}
	
	public static void main(String[] args) {
		
		SysSigleCrank sys = new SysSigleCrank();
		SingleDesign wd= new SingleDesign(sys);
		double [] lengths = wd.getPusherLengths(sys.bottomHeight,360);
		for(double l:lengths) {
			System.out.printf("length %6.2f\n", l);
		}
		
		//wd.findProfile();
		//wd.showProfile();
		//wd.findProfile();
		//wd.showProfile();
	}
}
