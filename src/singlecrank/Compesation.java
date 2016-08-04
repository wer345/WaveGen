package singlecrank;

import wave.Angle;

public class Compesation {
	double r=20;
	double l=40;
	double ab=Angle.d2r(140); // angle bias
	double s=33;
	
	double getd(double angle) {
		double a=angle-ab;
		double h=r*Math.sin(a);
		double d=r*Math.cos(a)+Math.sqrt(l*l-h*h);
		return d;
	}

	public static void main(String[] args) {
		Compesation c= new Compesation();
		Samples ss=new Samples();
		int nofS=30;
		double step=2*Math.PI/nofS;
		
		for(int i=0;i<nofS;i++) {
			double angle=step*i;
			double d=c.getd(angle);
			ss.add(-d*c.s);
		}
		
		Freqs fs = new Freqs(ss,6);
		System.out.printf("freqs cmps =%s\n", fs);

	}

}
