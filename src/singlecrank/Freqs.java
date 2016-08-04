package singlecrank;

import java.util.ArrayList;
import java.util.List;

import wave.ValueList;
import Jama.Matrix;

public class Freqs {
	public double mean=0;
	public List <Freq> fs=new ArrayList <Freq>();
	
	Freqs() {
		
	}
	
	Freqs(Samples ys,int level) {
		ValueList vs=getSinParams(ys,level);
		int idx=0;
		mean=vs.get(idx++);
		for(int i=0;i<level;i++) {
			fs.add(new Freq(vs.get(idx++),vs.get(idx++)));
		}
	}
	
	void addFreq(Freq f) {
		fs.add(f);
	}
	
	void addFreqs(double... args) {
		int size=args.length/2;
		for(int i=0;i<size;i++)
			fs.add(new Freq(args[i+i],args[i+i+1]));
	}
	
	Samples generateSamples(int nofSamples) {
		Samples rst= new Samples();
		double step=2*Math.PI/nofSamples;
		for(int i=0;i<nofSamples;i++) {
			double a=i*step;
			int idxP=0;
			double v=mean;
			for(int l=0;l<fs.size();l++) {
				v+=fs.get(idxP++).value((l+1)*a);
			}
			rst.add(v);
		}
		return rst;
	}

	public String toString() {
		String rst=String.format("%6.2fm", mean);
		for(Freq f:fs) {
			rst+=", "+f.toString();
		}
		return rst;
	}
	
	static public ValueList getSinParams(ValueList ys,int level) {
		
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
	
	public static void main(String[] args) {
		Freqs fs= new Freqs();
		fs.mean=10;
//		fs.addFreq(new Freq(1.5,1));
//		fs.addFreq(new Freq(0.6,0.7));
		fs.addFreqs(1.5,1,0.6,0.7);;
		Samples ss = fs.generateSamples(8);
		System.out.printf("samples=%s\n", ss);
		Freqs fs1=new Freqs(ss,2);
		System.out.printf("get freqs from sample=%s\n", fs1);
	}
}
