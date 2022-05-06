import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner; 
import java.util.concurrent.TimeUnit;
 
public class AutoDataNodeClient {
	public static void main(String[] args) throws Exception{
		//String msg = "Client data";
		while(true){
			TimeUnit.SECONDS.sleep(3);
			String line = "dfs.encrypt.data.transfer,true";
			System.out.println("reconfigute: "+line);
			if(line.equals("q")) break;
			try{
				Socket socket = new Socket("127.0.0.1",9525);
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				pw.println(line);
				pw.flush();
				line = is.readLine();
				System.out.println("received from server:"+line);
				pw.close();
				is.close();
				socket.close();
			} catch(IOException e){
				//e.printStackTrace();
				System.out.println("port not open");
			}
			
		}
		

	}
}