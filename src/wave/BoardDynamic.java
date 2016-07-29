package wave;


public class BoardDynamic {
	Spectrum driveAngle= new Spectrum();
	Spectrum driverEnd_X= new Spectrum();
	Spectrum driverEnd_Y= new Spectrum();
	
	Spectrum boardStart_X= new Spectrum();
	Spectrum boardStart_Y= new Spectrum();
	
	Spectrum boardEnd_X= new Spectrum();
	Spectrum boardEnd_Y= new Spectrum();
	
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
