
public class W2V {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double a=1;
		double c=-1;
		double d=27;
		
		double ruo,S,k,H,g;
		ruo=1000;
		S=2;
		k=1;
		H=0.1;
		g=9.81;
		
		a=ruo*S*k/2;
		c=-ruo*S*g*H;
		d=20000;
		//c=0;
		
		double y=-108*a*a*d;
		double z=y*y+Math.pow(12*a*c, 3);
		double zs=Math.sqrt(z);
		double v;
		if(y+zs>=0)
			v=Math.pow(y+zs, 1/3.0);
		else
			v=-Math.pow(-y-zs, 1/3.0);
		if(y>zs)
			v+=Math.pow(y-zs, 1/3.0);
		else
			v-=Math.pow(zs-y, 1/3.0);
			
		v/=6*a;
		
		System.out.printf("v=%f\n",v);
		
	}

}
