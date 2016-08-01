package physics;

public class ContactBoard extends Joint {

	public ContactBoard(Point fix,Point free,double p,double q,int side) {
		super(fix,free,p,q,side);
	}
	
	public ContactBoard(Point fix,Point free,Point joint ) {
		super(fix,free,joint);
	}
}
