package wave;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Line;
import physics.Point;
import view.VLine;
import view.VPolyline;

public class WaveBase {
	public double crankAngle=0;
	public double animStep=0.01*Math.PI;
	public double maxAngle=2*Math.PI;
	public double angle90=Math.PI/2;
	
	
	double	crankHeight=100;
	Point	crankCenter= new Point(0,crankHeight);
	double	crankRadius=20;
	
	public	Point boardFix;
	public	BoardData compBoard;
	public	int nofBoard;
	public	List <BoardData> boards= new ArrayList<BoardData>();

	double	bottomHeight=43;
	
//	public int getNofBoard()
//	{
//		return boards.size();
//	}
	
	public List<VLine> getLineViews()
	{
		return null;
	}
	
	public List<VPolyline> getPolylineViews()
	{
		List<VPolyline>  polylines = new ArrayList<VPolyline>(); 
	    for(int idxPusher=0;idxPusher<nofBoard;idxPusher++) {
	    	BoardData bd = boards.get(idxPusher);
	    	
			VPolyline vpl= new VPolyline(bd.profile); 
			vpl.setColor(Color.yellow);
			vpl.setSize(3);
			polylines.add(vpl);
	    }
		return polylines;
	}
	
	public void run(int nofBoardToRun) {
	}
	
	
	public void run() {
		run(nofBoard);
	}
	public void start()
	{
		crankAngle=0;
		run();
	}

	public boolean next()
	{
		double newCrankAngle = crankAngle + animStep;
		if(newCrankAngle <= maxAngle) {
			crankAngle = newCrankAngle;
			run();
			return true;
		}
		return false;
	}
	
}
