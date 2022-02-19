package telran.calculator.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import telran.calculator.service.Calculator;
import telran.calculator.service.CalculatorImpl;

public class CalculatorServerAppl {

	private static final int PORT = 2000;

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server is listening on port " + PORT);
		while (true) {
			Socket socket = serverSocket.accept();
			runCalculation(socket);
		}
	}

	private static void runCalculation(Socket socket) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream writer = new PrintStream(socket.getOutputStream())) {
			while (true) {
				String line = reader.readLine();// getting request from the client
				if (line == null) {
					System.out.println("graceful closing connection");
					break;
				}
				line = getResponse(line);
				writer.println(line);
			}
		} catch (Exception e) {
			System.out.println("client closed connection abnormally");
		}

	}

	private static String getResponse(String line) {
		String[] tokens = line.split("#");
		Set<String> operations = new HashSet<>(Arrays.asList("+", "-", "*", "/"));
		String res = "";
		Calculator calc = new CalculatorImpl();
		if (tokens.length != 3) {
			res = "wrong request";
		} else if (!operations.contains(tokens[0])) {
			res = "Unknown request";
		} else {
			res += calc.compute(tokens[0], Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
		}
		return res;
	}

}