public class SplitTest{
	public static void main(String[] args){
		String line="tkl,ss";
		String[] property_newVal_pair=line.split("&");
		System.out.println(property_newVal_pair.length);
		System.out.println(property_newVal_pair[0]);
	}
 }