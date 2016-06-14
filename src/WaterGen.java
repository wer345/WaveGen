
public class WaterGen {
	static double g=9.81;
	double //ruo,S,k,H,g,W,p,q;
	ruo=1000,
	S=2,
	k=1,
	H=3,
	W=50*1000;
	
	public double findVelocity() {
		double p,q;
		p=-2*g*H/k;
		q=2*W/(k*ruo*S);
		Double[] solves=Cardano.realSolve(p,q);
		double rst= -1.0;
		for(double v:solves) {
			if(v>=0) {
				if(rst<0)
					rst=v;
				else {
					if(v<rst)
						rst=v;
				}
			}
		}
		return rst;
	}
	public static void main(String[] args) {
		WaterGen wg=new WaterGen();
		double t=0;
		double tstep=60;
		double A=10000;
		for(;t<7200;) {
			double v=wg.findVelocity();
			if(v>=0) {
				System.out.printf("t=%6.0f,x=%f,H=%f\n", t/60, v,wg.H);
				double Vol=wg.S*v*tstep;
				wg.H-=Vol/A;
				t+=tstep;
			}
			else {
				System.out.printf("x=%f,H=%f\n", v,wg.H);
				break;
			}
				
			
		}

	}

}
