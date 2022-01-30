import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner; 
 
public class BroadCastClient {
	public static void main(String[] args){
		//System.out.println(args[0]+","+args[1]);
		try {
			ServerSocket server = new ServerSocket(9524);
			while (true) {
				Socket socket = server.accept();
				BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				String line = is.readLine();
				System.out.println(line);
				//pw.printf("dfs.encrypt.data.transfer,true");
				pw.printf(args[0]);
				pw.flush();

				pw.close();
				is.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			//server.close();
		}
	}
}