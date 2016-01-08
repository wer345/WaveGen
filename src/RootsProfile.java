
public class RootsProfile {

	static double square(double x)
	{
		return x*x;
	}
	static double delta(double t,double r)
	{
		return square(1-0.866*(t-r))+square(0.5*(t-r))-4*r*r;
		//return 1-0.86*(t-r)+t*t-2*t*r-3*r*r;
	}
	
	// when t=0.9, r=0.2783
	// when t=0.85, r=0.2942
	// when t=0.8, r=0.3140
	
	public static void main(String[] args) {
		double t=8.0/10.0;
		double lastd=0;
		double r1=0.312;
		double r2=0.315;
		double step=(r2-r1)/30;
		for(double r=r1;r<r2;r+=step) {
			double d=delta(t,r);
			System.out.printf("r=%f, d=%f\n", r, d);
			if(d*lastd<0)
				break;
			lastd=d;
		}
	}

}
