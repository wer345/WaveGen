import Wave.BoardData;


public class BoardDynamic {
	ValueList driveAngle= new ValueList();
	ValueList driverEnd_X= new ValueList();
	ValueList driverEnd_Y= new ValueList();
	
	ValueList boardStart_X= new ValueList();
	ValueList boardStart_Y= new ValueList();
	
	ValueList boardEnd_X= new ValueList();
	ValueList boardEnd_Y= new ValueList();
	
	public double driveMass=1;
	public double BoardMass=1;
	public double pivotInertia=1; // Pivot's inertia around driverEnd;
	public double driveInertia=1; // Drive's inertia around driverEnd;
	public double boardInertia=1; // Board's inertia around BoardStart;
	
	BoardDynamic() {
		
	}
	
	void addData(BoardData bd) {
		driveAngle.add(bd.driverAngle);
		driverEnd_X.add(bd.driverEnd.x);
		driverEnd_Y.add(bd.driverEnd.y);
		boardStart_X.add(bd.boardStart.x);
		boardStart_Y.add(bd.boardStart.y);
		boardEnd_X.add(bd.boardEnd.x);
		boardEnd_Y.add(bd.boardEnd.y);
	}
}
