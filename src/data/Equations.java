package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import wave.AnimWave;

import com.utils.HashList;
import com.utils.Util;

public class Equations {

	HashList <String,String> lstEqs = new HashList <String,String>();
	
	String readAFullLine(BufferedReader br) throws Exception {
		String result="";
		int cnt=0; // count of charact "
		while(true) {
			String line = br.readLine();
			if(line==null)
				return null;
			result+=line;
			for(int i=0;i<line.length();i++)
				if(line.charAt(i)=='"')
					cnt++;
			if(cnt%2==0)
				break;
			result+="\n";
		}
		return result;
	}
	
	public int Load(String fileName) {
		String line = "";
		int err=0;
		
		lstEqs.clear();
		BufferedReader br=null;
		try {
			
			File fileDir = new File(fileName);
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			
			
			InputStream in = new FileInputStream(fileName);

			while ((line = br.readLine()) != null) {
				int pos1=line.indexOf("\"");
				int pos2;
				if(pos1>=0) {
					pos2=line.indexOf("\"",pos1+1);
					if(pos2>0) {
						int posEq=line.indexOf("=",pos2+1);
						if(posEq>0) {
							String key=line.substring(pos1+1,pos2).trim().toLowerCase();
							String value=line.substring(posEq+1).trim();
							lstEqs.add(key,value);
						}
					}
				}
					
			}
	 
		} catch (Exception e) {
			e.printStackTrace();
			err=1;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return err;
	}

	public double getValue(String key) throws Exception {
		String strV= lstEqs.get(key.toLowerCase().trim());
		if(strV==null) {
			System.out.printf("[ERROR} There is no value for %s\n", key);
			throw new Exception("No value found");
		}
		return Double.parseDouble(strV);
	}
	
	@Override
	public String toString() {
		return "Equations [\n" + lstEqs + "]";
	}

	public static void _main(String[] args) {
		Equations eq= new Equations();
		eq.Load("data\\eqSide.txt");
		System.out.printf("%s\n", eq);
		try {
			double v=eq.getValue("XC_Push5");
			System.out.printf("valus is %f\n", v);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
