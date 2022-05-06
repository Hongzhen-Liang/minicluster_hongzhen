

public class Generic{

	public static double expand(double tClass){
		double weight=0.001;
		return tClass*weight;
	}
	
	public static void main(String args[]){
		System.out.println("good");
		int a=10;
		a=(int)expand((double)a);
		System.out.println(a);
	}

}