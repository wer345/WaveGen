package physics;

public class ContactBoard extends Joint {
	static double maxHeight=200;
	public double boardLength;				// the length of board
	public int 		nofProfilePoint; 		// the number of points that are between the 2 ends of board that give the profile of board
	public double [] profileHeights;		// the heights for profile point to the board line 
	public Polyline profile;
	
	public ContactBoard(Point fix,Point free,double p,double q,int side,int n) {
		super(fix,free,p,q,side);
		initProfile(n);
	}
	
	public ContactBoard(Point fix,Point free,Point joint, int n) {
		super(fix,free,joint);
		initProfile(n);
	}
	
	void initProfile(int n)
	{
		nofProfilePoint=n;
		profileHeights = new double[nofProfilePoint];
		for(int i=0;i<nofProfilePoint;i++) {
			profileHeights[i]=maxHeight;
		}
		
		profile = new Polyline(nofProfilePoint+2);
		boardLength=q;
	}
	
	// set profile height let it not below the bottom line
	public void alignProfileHeight(double bottomLevel) {
		Vector boardDirection=fix.vectorTo(joint);
		boardDirection.normalize();
		Vector boardNormal= new Vector(boardDirection.y, -boardDirection.x);
		
		double nofSection=nofProfilePoint+1;
		double senctionLength=boardLength/nofSection;
		double distance=senctionLength; // the distance from BoardStart to profile point's project of the board line
		for(int i=1;i<=nofProfilePoint;i++) {
			
			double heightToBottom= (bottomLevel-fix.y-distance*boardDirection.y)/boardNormal.y;
			if(profileHeights[i-1]>heightToBottom) 
				profileHeights[i-1]=heightToBottom;
			distance+=senctionLength;
		}
		
	}
	
	public void update() {
		super.update();
		
		profile.set(0, fix.x, fix.y);
		Vector boardDirection=fix.vectorTo(joint);
		boardDirection.normalize();
		Vector boardNormal= new Vector(boardDirection.y, -boardDirection.x);
		
		double nofSection=nofProfilePoint+1;
		double senctionLength=boardLength/nofSection;
		double distance=senctionLength;
		for(int i=1;i<=nofProfilePoint;i++) {
			Point p =profile.points.get(i);
			p.set(	fix.x+distance*boardDirection.x+profileHeights[i-1]*boardNormal.x,
					fix.y+distance*boardDirection.y+profileHeights[i-1]*boardNormal.y);
			distance+=senctionLength;
		}
		profile.set(nofProfilePoint+1, joint.x, joint.y);
		
	}
	
}
