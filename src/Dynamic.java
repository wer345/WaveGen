import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import Physics.Point;
import Physics.Vector;
import Wave.BoardData;
import Wave.WaveData;


public class Dynamic extends WaveData{

	
	public void checkDynamic() {
		int resolution=360;
		double step=2*Math.PI/resolution;
		
		crankAngle=step*1;
		BoardDynamic [] bds = new BoardDynamic[nofBoard];
		for(int i=0;i<nofBoard;i++) {
			bds[i]=new BoardDynamic(); 
		}
		

		for (int a=0;a<=360;a++) {
			crankAngle=step*a;
			run(nofBoard);
			for(int i=1;i<nofBoard;i++) {
				BoardData bd = boards.get(i);
				if(i==1)
					System.out.printf("%8.4f,%8.4f",bd.boardStart.x,bd.boardStart.y);
				System.out.printf("  ,%8.4f,%8.4f",bd.boardEnd.x,bd.boardEnd.y);
			}
			System.out.printf("\n");
		}
		
	}
	
	public static void main(String[] args) {
		//getPusherLength();
		Dynamic vlm= new Dynamic();
		vlm.checkDynamic();
	}
	
}
