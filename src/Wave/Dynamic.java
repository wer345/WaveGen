package Wave;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import Physics.Point;
import Physics.Vector;


public class Dynamic extends WaveData{

	Spectrum 
		crankP1x,crankP1y,
		crankP2x,crankP2y,
		syncP1x,syncP1y,
		syncP2x,syncP2y,
		syncAngle1,syncAngle2;
	BoardDynamic [] bds;
	
	Dynamic() {
		bds = new BoardDynamic[nofBoard];
		clear();
	}

	void clear() {
		crankP1x= new Spectrum();
		crankP1y= new Spectrum();
		crankP2x= new Spectrum();
		crankP2y= new Spectrum();
		syncP1x= new Spectrum();
		syncP1y= new Spectrum();
		syncP2x= new Spectrum();
		syncP2y= new Spectrum();
		syncAngle1= new Spectrum();
		syncAngle2= new Spectrum();
		
		for(int i=0;i<nofBoard;i++) {
			bds[i]=new BoardDynamic(); 
		}
	}
	
	public void checkDynamic() {
		int resolution=360;
		double step=2*Math.PI/resolution;
		
		crankAngle=step*1;
		

		for (int a=0;a<360;a++) {
			crankAngle=step*a;
			run(nofBoard);
			crankP1x.add(crankP1.x);
			crankP1y.add(crankP1.y);
			crankP2x.add(crankP2.x);
			crankP2y.add(crankP2.y);
			
			syncP1x.add(syncP1.x);
			syncP1y.add(syncP1.y);
			syncP2x.add(syncP2.x);
			syncP2y.add(syncP2.y);
			
			syncAngle1.add(angleSync1);
			syncAngle2.add(angleSync2);

			for(int i=0;i<nofBoard;i++) {
				BoardData bd = boards.get(i);
				bds[i].addData(bd);
//				if(i==1)
//					System.out.printf("%8.4f,%8.4f",bd.boardStart.x,bd.boardStart.y);
//				System.out.printf("  ,%8.4f,%8.4f",bd.boardEnd.x,bd.boardEnd.y);
			}
//			System.out.printf("\n");
		}
		int level=3;
		crankP1x.setFreqs(level);
		crankP1y.setFreqs(level);
		crankP2x.setFreqs(level);
		crankP2y.setFreqs(level);
		syncP1x.setFreqs(level);
		syncP1y.setFreqs(level);
		syncP2x.setFreqs(level);
		syncP2y.setFreqs(level);
		syncAngle1.setFreqs(level+3);
		syncAngle2.setFreqs(level+3);
		
		System.out.printf("crankP1x freq=%s\n",crankP1x.freqs);
		System.out.printf("crankP1y freq=%s\n",crankP1y.freqs);
		System.out.printf("crankP2x freq=%s\n",crankP2x.freqs);
		System.out.printf("crankP2y freq=%s\n",crankP2y.freqs);
		System.out.printf("syncP1x freq=%s\n",syncP1x.freqs);
		System.out.printf("syncP1y freq=%s\n",syncP1y.freqs);
		System.out.printf("syncP2x freq=%s\n",syncP2x.freqs);
		System.out.printf("syncP2y freq=%s\n",syncP2y.freqs);
		System.out.printf("syncAngle1 freq=%s\n",syncAngle1.freqs);
		System.out.printf("syncAngle1 freq=%s\n",syncAngle2.freqs);
	}
	
	public static void main(String[] args) {
		//getPusherLength();
		Dynamic vlm= new Dynamic();
		vlm.checkDynamic();
	}
	
}
