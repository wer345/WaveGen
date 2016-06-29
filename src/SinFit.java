import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Jama.Matrix;


public class SinFit {

	public void findVolume() {
		Scanner scan;
	    File file = new File("data\\volume_360.txt");
	    ValueList ys= new ValueList();
	    try {
	        scan = new Scanner(file);

	        while(scan.hasNextDouble())
	        {
	        	ys.add(scan.nextDouble());
	        }
	        
	    } catch (FileNotFoundException e1) {
	            e1.printStackTrace();
	    }

//	    for(int i=0;i<ys.size();i++) {
//	    	double d=ys.get(i);
//	    	System.out.printf("%d,%6.2f\n", i,d);
//	    }
//	    
//    	double sum=ys.sum();
//    	System.out.printf("sum is %6.2f\n", sum);

	    ValueList s= new ValueList();
	    ValueList c= new ValueList();
	    ValueList s2= new ValueList();
	    ValueList c2= new ValueList();

	    int n=ys.size();
	    double step=2*Math.PI/n;
	    for(int i=0;i<n;i++) {
	    	s.add(Math.sin(step*i));
	    	c.add(Math.cos(step*i));
	    	s2.add(Math.sin(2*step*i));
	    	c2.add(Math.cos(2*step*i));
	    }
	    ValueList xs= new ValueList(n,0.0,1.0);
	    
	    boolean doSecond=true;
		int size=4;
		
		if(doSecond)
			size=6;
		
		Matrix A = new Matrix(size,size);
		Matrix b = new Matrix(size,1);
		
		A.set(0, 0, n);
		A.set(0, 1, xs.sum());
		A.set(0, 2, 0);
		A.set(0, 3, 0);
	    b.set(0, 0, ys.sum());
	    
		A.set(1, 0, xs.sum());
		A.set(1, 1, ValueList.mul(xs, xs).sum());
		A.set(1, 2, ValueList.mul(s, xs).sum());
		A.set(1, 3, ValueList.mul(c, xs).sum());
	    b.set(1, 0, ValueList.mul(ys, xs).sum());

		A.set(2, 0, s.sum());
		A.set(2, 1, ValueList.mul(xs, s).sum());
		A.set(2, 2, ValueList.mul(s, s).sum());
		A.set(2, 3, ValueList.mul(c, s).sum());
	    b.set(2, 0, ValueList.mul(ys, s).sum());

		A.set(3, 0, c.sum());
		A.set(3, 1, ValueList.mul(xs, c).sum());
		A.set(3, 2, ValueList.mul(s, c).sum());
		A.set(3, 3, ValueList.mul(c, c).sum());
	    b.set(3, 0, ValueList.mul(ys, c).sum());

		if(doSecond) {
			A.set(0, 4, 0);
			A.set(0, 5, 0);

			A.set(1, 4, ValueList.mul(s2, xs).sum());
			A.set(1, 5, ValueList.mul(c2, xs).sum());
			
			A.set(2, 4, ValueList.mul(s2, s).sum());
			A.set(2, 5, ValueList.mul(c2, s).sum());

			A.set(3, 4, ValueList.mul(s2, c).sum());
			A.set(3, 5, ValueList.mul(c2, c).sum());

			A.set(4, 0, s.sum());
			A.set(4, 1, ValueList.mul(xs, s2).sum());
			A.set(4, 2, ValueList.mul(s, s2).sum());
			A.set(4, 3, ValueList.mul(c, s2).sum());
			A.set(4, 4, ValueList.mul(s2, s2).sum());
			A.set(4, 5, ValueList.mul(c2, s2).sum());
		    b.set(4, 0, ValueList.mul(ys, s2).sum());

			A.set(5, 0, c.sum());
			A.set(5, 1, ValueList.mul(xs, c2).sum());
			A.set(5, 2, ValueList.mul(s, c2).sum());
			A.set(5, 3, ValueList.mul(c, c2).sum());
			A.set(5, 4, ValueList.mul(s2, c2).sum());
			A.set(5, 5, ValueList.mul(c2, c2).sum());
		    b.set(5, 0, ValueList.mul(ys, c2).sum());
			
		}
	    
	    
		Matrix pqef= A.solve(b);
		for(int row=0;row<size;row++) {
			double  v=pqef.get(row,0);
			System.out.printf("pqef%d= %6.2f\n", row,v);
		}

		
		double p=pqef.get(0, 0);
		double q=pqef.get(1, 0);
		double e=pqef.get(2, 0);
		double f=pqef.get(3, 0);
		
		double r=0;
		double t=0;
		if(doSecond) {
			r=pqef.get(4, 0);
			t=pqef.get(5, 0);
		}
		
		for(int i=0;i<n;i++) {
			double calc=p+q*i+e*s.get(i)+f*c.get(i);
			if(doSecond) {
				calc+=r*s2.get(i)+t*c2.get(i);
			}
			double diff=calc-ys.get(i);
			double all=ys.get(i) -(e*s.get(i)+f*c.get(i));
			System.out.printf("%d,  %6.2f,  %6.2f,  %6.4f,  %6.2f\n", i,ys.get(i), calc,diff,all);
		}
	}

	void solver()
	{
		int size=4;
		Matrix A = new Matrix(size,size);
		Matrix b = new Matrix(size,1);
		for(int row=0;row<size;row++) {
			for(int col=0;col<size;col++) {
				if(row==col) {
					A.set(row, col, 1);
				}
				else 
					A.set(row, col, 0);
			}
			b.set(row, 0, row+1);
		}
		A.set(0, 3, 1);
		Matrix x= A.solve(b);
		for(int row=0;row<size;row++) {
			double  v=x.get(row,0);
			System.out.printf("x%d= %6.2f\n", row,v);
		}
		
	}
	
	public static void main(String[] args) {
		//getPusherLength();
		SinFit sf= new SinFit();
		sf.findVolume();
		//sf.solver();
	}
	
}
