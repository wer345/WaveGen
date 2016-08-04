package singlecrank;

public class Freq {
	public double s;
	public double c;
	
	Freq()
	{
		s=1;
		c=0;
	}
	
	Freq(double s, double c) {
		this.s=s;
		this.c=c;
	}

	public double amplify() {
		return Math.sqrt(s*s+c*c);
	}
	
	public double value(double angle) {
		return s*Math.sin(angle)+c*Math.cos(angle);
	}
	
	public String toString() {
		String sign="+";
		if(c<0)
			sign="";
		return String.format("%6.4fs%s%2.4fc",s,sign,c);
	}
	
}
