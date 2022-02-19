package telran.net;
import java.net.*;
import java.io.*;
import telran.view.*;
public class ReverseLengthClientAppl {
private static final int PORT = 2000;
static PrintStream writer;
static BufferedReader reader;
	public static void main(String[] args) throws Exception{
		InputOutput io = new ConsoleInputOutput();
		Socket socket = new Socket("localhost", PORT);//establishing connection with the server running on localhost with port 2000
		writer = new PrintStream(socket.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		Menu menu = new Menu("Reverse-Length Application", Item.of("Send request",
				ReverseLengthClientAppl::sendRequest), Item.of("Exit", iop->{
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}, true));
		menu.perform(io);
	}
	static void sendRequest(InputOutput io) {
		String request = io.readString("Enter request");
		String string = io.readString("Enter string");
		writer.println(String.format("%s#%s", request,string));
		try {
			String response = reader.readLine();
			io.writeObjectLine(response);
			
		} catch (IOException e) {
			throw new RuntimeException(e.toString());
		}
	}

}