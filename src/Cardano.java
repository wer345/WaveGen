import java.util.ArrayList;
import java.util.List;
//import org.apache.commons.math3.complex.ComplexFormat;

public class Cardano {

	//find real solution of x^3 + px+q=0
	static double cubicRoot(double x)
	{
		return Math.cbrt(x);
	}
	// https://www.math.ucdavis.edu/~kkreith/tutorials/sample.lesson/cardano.html
	
	static Complex [] complexSolve(double p,double q) {
		List <Complex> resultsComplex = new ArrayList<Complex>();
		// let x=u+v
		// get u^3+v^3+(u+v)(3uv+p)+q=0
		// set 3uv+p=0, get
		// let u'=u^3, v'=v^3
		// u'+v'=-q
		// u'*v'=-p^3/27
		// u'*(-q-u')=-p^3/27
		// u'^2+qu'=p^3/27
		// (u'+q/2)=p^3/27-q^2/4
		// u'= -q/2+sqrt(p^3/27-q^2/4);
		
		//System.out.printf("p=%f; q=%f\n", p, q);
		Complex c=new Complex(p*p*p/27+q*q/4, 0);
		Complex ucub,vcub;
		Complex[] csqrs=c.sqrt();
		for(Complex csqr:csqrs) {
			ucub = new Complex(-q/2,0);
			ucub = ucub.plus(csqr);
			vcub=new Complex(-q,0);
			vcub=vcub.minus(ucub);
			//System.out.printf("ucub=%s; vcub=%s\n", ucub, vcub);
			
			Complex[] uArray=ucub.cubrt();
			Complex[] vArray=vcub.cubrt();
			for(Complex u:uArray) {
				for(Complex v:vArray) {
					Complex x=u.plus(v);
					//System.out.printf("u=%s; v=%s,x=%s\n", u, v, x);
					boolean isNew=true;
					for(Complex ck:resultsComplex) {
						if(ck.equals(x)) {
							isNew=false;
							break;
						}
					}
					if(isNew) {
						resultsComplex.add(x);
					}
					else {
						//System.out.printf("Dup\n");
					}
				}
			}
		}
		Complex [] r= new Complex[resultsComplex.size()];
		resultsComplex.toArray(r);
		return r;
	}
	
	static Double [] realSolve(double p,double q) {
		List <Double> results = new ArrayList<Double>();
		Complex [] resultsComplex=complexSolve(p,q);
		//System.out.printf("Results:\n"); 
		for(Complex x:resultsComplex) {
			//System.out.printf("x=%s\n",  x);
			if(Math.abs(x.imag())<1e-8)
				results.add(x.real());
		}
		Double [] r= new Double[results.size()];
		results.toArray(r);
		return r;
	}
	
	public static void main(String[] args) {
		double ruo,S,k,H,g,W,p,q;
		ruo=1000;
		S=2;
		k=1;
		H=3;
		g=9.81;
		W=165*1000;
		
		p=-2*g*H/k;
		q=2*W/(k*ruo*S);
		Double[] rst=realSolve(p,q);
		System.out.printf("Real Results:\n"); 
		for(double x:rst) {
			System.out.printf("x=%f\n", x);
		}
	}

}
