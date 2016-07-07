package Wave;
import Jama.Matrix;


public class Spectrum {
	public ValueList data = new ValueList();	
	public ValueList freqs = new ValueList();
	
	Spectrum() {
		
	}
	
	void add(double v) {
		data.add(v);
	}
	
	void setData(int nofData){
		data=getData(freqs,nofData);
	}

	void setData(){
		data=getData(freqs,data.size());
	}

	void setFreqs(int level){
		freqs=getSinParams(data,level);
	}
	
	static ValueList getData(ValueList params, int nofData) {
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

	
}
