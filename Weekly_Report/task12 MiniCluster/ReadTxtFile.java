import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ReadTxtFile{
	
	
	public static void readTxtFile(String filePath){
		try{
			File file=new File(filePath);
			if(file.isFile() && file.exists()){
				String encoding="GBK";
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine())!=null){
					String[] words = lineTxt.split(","); 
					System.out.println(words[0]+" "+words[1]);
				}
				read.close();
			}
		} catch(Exception e){
			System.out.println("读取文件内容出错");
		}
	}

	public static void main(String args[]){
		String filePath="./InitialConf.txt";
		readTxtFile(filePath);
	}


}