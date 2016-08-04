package singlecrank;

import Jama.Matrix;
import wave.ValueList;

public class FreqsLinear extends Freqs {
	double k=0;
	
	public FreqsLinear() {
		
	}
	
	public FreqsLinear(ValueList ys,int level) {
		ValueList vs= getXSinParams(ys, level);
		mean=vs.get(0);
		k=vs.get(1);
		for(int i=2;i<vs.size();i+=2) {
			addFreq(new Freq(vs.get(i),vs.get(i+1)));
		}
	}

	public Samples generateSamples(int nofSamples) {
		Samples ss=super.generateSamples(nofSamples);
		double step=2*Math.PI/nofSamples;
		for(int i=0;i<nofSamples;i++) {
			double a=i*step;
			ss.add(i,k*a);
		}
		return ss;
	}
	
	public String toString() {
		String rst=String.format("%2.4fm, %2.4fk", mean,k);
		for(Freq f:fs) {
			rst+=", "+f.toString();
		}
		return rst;
	}
	
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
		
		
	    ValueList xs= new ValueList();
	    xs.setLinear(n,0.0,1.0);
	    
		int mtxSize=2+2*level;
		
		
		Matrix A = new Matrix(mtxSize,mtxSize);
		Matrix b = new Matrix(mtxSize,1);
		
		//Set first equation 
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
			if(irow==1) 
				rst.add(x.get(irow,0)*n/(2*Math.PI));
			else
				rst.add(x.get(irow,0));
		}
		
		return rst;
	}
	
	public static void main(String[] args) {
		FreqsLinear fs= new FreqsLinear();
		fs.mean=10;
		fs.k=0.123;
//		fs.addFreq(new Freq(1.5,1));
//		fs.addFreq(new Freq(0.6,0.7));
		fs.addFreqs(1.5,1,0.6,0.7);
		Samples ss = fs.generateSamples(8);
		System.out.printf("samples=%s\n", ss);
		FreqsLinear fs1=new FreqsLinear(ss,2);
		System.out.printf("get freqs from sample=%s\n", fs1);
	}
	
	
}
