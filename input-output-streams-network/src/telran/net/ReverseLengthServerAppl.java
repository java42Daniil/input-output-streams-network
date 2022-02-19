package telran.net;
import java.net.*;
import java.io.*;
public class ReverseLengthServerAppl {
private static final int PORT = 2000;

public static void main(String[] args) throws Exception{
	ServerSocket serverSocket = new ServerSocket(PORT);
	System.out.println("Server is listening on port " + PORT);
	while(true) {
		Socket socket = serverSocket.accept(); //waiting for a connection with a client
		//The connection is established and socket contains reference to the object,
		//though which server will communicate with client
		runApplProtocol(socket);
	}
}

private static void runApplProtocol(Socket socket) {
	
	try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()) );
			PrintStream writer = new PrintStream(socket.getOutputStream())){
		while(true) {
			String line = reader.readLine(); //getting request from the client
			if (line == null) {
				System.out.println("graceful closing connection");
				break;
			}
			line = getResponse(line); //sending response to the client
			writer.println(line); 
		}
	} catch (Exception e) {
		System.out.println("client closed connection abnormally");
	}
	
}

private static String getResponse(String line) {
	String tokens[] = line.split("#");
	String res = "";
	if (tokens.length != 2) {
		res = "Wrong request";
	} else {
		switch(tokens[0]) {
		case "length" : res += tokens[1].length(); break;
		case "reverse" : res = new StringBuilder(tokens[1]).reverse().toString(); break;
		default: res = "Unknown request";
		}
	}
	return res;
}
}