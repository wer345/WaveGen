
public class Complex_test {

	public static void testSquareRoot(){
		Complex a=new Complex(1,1);
		Complex [] aR= a.sqrt();
		for(int i=0;i<2;i++) {
			Complex ar=aR[i];
			System.out.printf("ar =%s\n", ar);
			Complex aa = ar.times(ar);
			System.out.printf("a =%s\naa=%s\n", a,aa);
			if(a.equals(aa))
				System.out.printf("PASS\n");
		}
		
	}
	
	public static void testCubicRoot(){
		Complex a=new Complex(1,1);
		Complex [] aR= a.cubrt();
		for(int i=0;i<3;i++) {
			Complex ar=aR[i];
			System.out.printf("ar =%s\n", ar);
			Complex aa = ar.times(ar).times(ar);
			System.out.printf("a =%s\naa=%s\n", a,aa);
			if(a.equals(aa))
				System.out.printf("PASS\n");
		}
		
	}
	public static void main(String[] args) {
		testSquareRoot();
		//testCubicRoot();
	}
}
