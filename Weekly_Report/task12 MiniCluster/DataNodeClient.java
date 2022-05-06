import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner; 
 
public class DataNodeClient {
	public static void main(String[] args){
		//String msg = "Client data";
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.print("Please input property,newVal:");
			String line = sc.nextLine();
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
				e.printStackTrace();
			}
			
		}
		sc.close();
		

	}
}