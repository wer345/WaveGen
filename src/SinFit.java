import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Jama.Matrix;


public class SinFit {

	/** get parameters with X in it
	 * @param ys  -- sample data
	 * @param level -- the level of parameter
	 * @return params in p0+x*P1 +P2*sin(w*x)+P3*cos(w*x) +P4*sin(2*w*x)+P5*cos(2*w*x)+ ... +P(m-1)*sin(L*w*x)+P(m)*cos(L*w*x)
	 */
	public ValueList getXSinParams(ValueList ys,int level) {
		
	    int n=ys.size();
	    double step=2*Math.PI/n;
		ValueList[] slist= new ValueList[level]; 
		ValueList[] clist= new ValueList[level];
		for(int idxLevel=0;idxLevel<level;idxLevel++) {
		    ValueList s= new ValueList();
		    ValueList c= new ValueList();
		    int factor=idxLevel+1;
		    double fs=factor*step;
		    for(int i=0;i<n;i++) {
		    	s.add(Math.sin(fs*i));
		    	c.add(Math.cos(fs*i));
		    }
		    slist[idxLevel]=s;
		    clist[idxLevel]=c;
		}
		
		
	    ValueList xs= new ValueList(n,0.0,1.0);
	    
		int mtxSize=2+2*level;
		
		
		Matrix A = new Matrix(mtxSize,mtxSize);
		Matrix b = new Matrix(mtxSize,1);
		
		//Set first equition 
		A.set(0, 0, n);
		A.set(0, 1, xs.sum());
		for(int i=2;i<mtxSize;i++) {
			A.set(0, i, 0);
		}
	    b.set(0, 0, ys.sum());
	    
		A.set(1, 0, xs.sum());
		A.set(1, 1, ValueList.mul(xs, xs).sum());
		{
			int col=2;
			for(int idxLvl=0;idxLvl<level;idxLvl++) {
				A.set(1, col++, ValueList.mul(slist[idxLvl], xs).sum());
				A.set(1, col++, ValueList.mul(clist[idxLvl], xs).sum());
			}
		}
	    b.set(1, 0, ValueList.mul(ys, xs).sum());

	    int row=2;
		for(int idxLvlR=0;idxLvlR<level;idxLvlR++) {
			A.set(row, 0, slist[idxLvlR].sum());
			A.set(row, 1, ValueList.mul(xs, slist[idxLvlR]).sum());
			int col=2;
			for(int idxLvlC=0;idxLvlC<level;idxLvlC++) {
				A.set(row, col++, ValueList.mul(slist[idxLvlC], slist[idxLvlR]).sum());
				A.set(row, col++, ValueList.mul(clist[idxLvlC], slist[idxLvlR]).sum());
			}
		    b.set(row, 0, ValueList.mul(ys, slist[idxLvlR]).sum());
		    row++;
		    
			A.set(row, 0, clist[idxLvlR].sum());
			A.set(row, 1, ValueList.mul(xs, clist[idxLvlR]).sum());
			col=2;
			for(int idxLvlC=0;idxLvlC<level;idxLvlC++) {
				A.set(row, col++, ValueList.mul(slist[idxLvlC], clist[idxLvlR]).sum());
				A.set(row, col++, ValueList.mul(clist[idxLvlC], clist[idxLvlR]).sum());
			}
		    b.set(row, 0, ValueList.mul(ys, clist[idxLvlR]).sum());
		    row++;
		}
		
	    
	    
		Matrix x= A.solve(b);
		
		ValueList rst=new ValueList();
		for(int irow=0;irow<mtxSize;irow++) {
			rst.add(x.get(irow,0));
		}
		
		return rst;
	}

	
	public ValueList getSinParams(ValueList ys,int level) {
		
	    int n=ys.size();
	    double step=2*Math.PI/n;
		ValueList[] slist= new ValueList[level]; 
		ValueList[] clist= new ValueList[level];
		for(int idxLevel=0;idxLevel<level;idxLevel++) {
		    ValueList s= new ValueList();
		    ValueList c= new ValueList();
		    int factor=idxLevel+1;
		    double fs=factor*step;
		    for(int i=0;i<n;i++) {
		    	s.add(Math.sin(fs*i));
		    	c.add(Math.cos(fs*i));
		    }
		    slist[idxLevel]=s;
		    clist[idxLevel]=c;
		}
		
		
	    
		int mtxSize=1+2*level;
		
		
		Matrix A = new Matrix(mtxSize,mtxSize);
		Matrix b = new Matrix(mtxSize,1);
		
		//Set first equation for constant 
		A.set(0, 0, n);
		for(int i=2;i<mtxSize;i++) {
			A.set(0, i, 0);
		}
	    b.set(0, 0, ys.sum());
	    
//		A.set(1, 0, xs.sum());
//		A.set(1, 1, ValueList.mul(xs, xs).sum());
//		{
//			int col=2;
//			for(int idxLvl=0;idxLvl<level;idxLvl++) {
//				A.set(1, col++, ValueList.mul(slist[idxLvl], xs).sum());
//				A.set(1, col++, ValueList.mul(clist[idxLvl], xs).sum());
//			}
//		}
//	    b.set(1, 0, ValueList.mul(ys, xs).sum());

	    int row=1;
		for(int idxLvlR=0;idxLvlR<level;idxLvlR++) {
			//Set the equation for sin
			A.set(row, 0, slist[idxLvlR].sum());
			int col=1;
			for(int idxLvlC=0;idxLvlC<level;idxLvlC++) {
				A.set(row, col++, ValueList.mul(slist[idxLvlC], slist[idxLvlR]).sum());
				A.set(row, col++, ValueList.mul(clist[idxLvlC], slist[idxLvlR]).sum());
			}
		    b.set(row, 0, ValueList.mul(ys, slist[idxLvlR]).sum());
		    row++;
		    
			//Set the equation for cos
			A.set(row, 0, clist[idxLvlR].sum());
			col=1;
			for(int idxLvlC=0;idxLvlC<level;idxLvlC++) {
				A.set(row, col++, ValueList.mul(slist[idxLvlC], clist[idxLvlR]).sum());
				A.set(row, col++, ValueList.mul(clist[idxLvlC], clist[idxLvlR]).sum());
			}
		    b.set(row, 0, ValueList.mul(ys, clist[idxLvlR]).sum());
		    row++;
		}
		
	    
	    
		Matrix x= A.solve(b);
		
		ValueList rst=new ValueList();
		for(int irow=0;irow<mtxSize;irow++) {
			rst.add(x.get(irow,0));
		}
		
		return rst;
	}

	
	public double verify(ValueList ys,ValueList params)
	{
		int err=0;
		int level= (params.size()-2)/2;
		int n=ys.size();
	    double step=2*Math.PI/n;
		for(int i=0;i<n;i++) {
			double x=step*i;
			double v=params.get(0)+params.get(1)*i;
			for(int idxLvl=0;idxLvl<level;idxLvl++) {
				int idxP=2+2*idxLvl;
				double a=(1+idxLvl)*x;
				v+=params.get(idxP)*Math.sin(a)+params.get(idxP+1)*Math.cos(a);
			}
			double e=v-ys.get(i);
			System.out.printf("%d,  %6.2f,  %6.2f,  %6.4f\n", i,ys.get(i), v,e);			
		}
		return err;
	}
	

	public static ValueList getValues(int nofSample,ValueList params) {
		ValueList rst= new ValueList();
	    double step=2*Math.PI/nofSample;
	    int level= (params.size()-2)/2;
	    
		for(int i=0;i<nofSample;i++) {
			double x=step*i;
			double v=params.get(0)+params.get(1)*i;
			for(int idxLvl=0;idxLvl<level;idxLvl++) {
				int idxP=2+2*idxLvl;
				double a=(1+idxLvl)*x;
				v+=params.get(idxP)*Math.sin(a)+params.get(idxP+1)*Math.cos(a);
			}
			rst.add(v);
		}
		return rst;
	}
	
	ValueList getData(ValueList params, int nofData) {
		ValueList rst= new ValueList();
		double step=2*Math.PI/nofData;
		int level=(params.size()-1)/2;
		for(int i=0;i<nofData;i++) {
			double a=i*step;
			int idxP=0;
			double v=params.get(idxP++);
			for(int l=0;l<level;l++) {
				v+=params.get(idxP++)*Math.sin((l+1)*a);
				v+=params.get(idxP++)*Math.cos((l+1)*a);
			}
			rst.add(v);
		}
		return rst;
	}
	
	public void test() {
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

	    ValueList params= getXSinParams(ys,5);
	    System.out.printf("%s\n", params);
	    ValueList p = new ValueList(3227.56,  -7.05, 524.63, -284.26,   7.47, -19.42); 
	}
	
	
	public void testSin() {
		Scanner scan;
		
		ValueList params_1 = new ValueList(1.0  ,   1 , 0.5,  0.1,0.2,  0.1,0.2);
		//params_1 = new ValueList(1.0  , 0.0 , 1.0, 0.0 , 1.0);
		
	    ValueList ys= getData(params_1,360) ;

	    ValueList params= getXSinParams(ys,3);
	    
	    System.out.printf("%s\n", params.toString("8.6"));
//	    ValueList p = new ValueList(3227.56,  -7.05, 524.63, -284.26,   7.47, -19.42); 
	 //   verify(ys,params);
	}
	

	
	public static void main(String[] args) {
		SinFit sf= new SinFit();
		sf.testSin();
	}
	
}
