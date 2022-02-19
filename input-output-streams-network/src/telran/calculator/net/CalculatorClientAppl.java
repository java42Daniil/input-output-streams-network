package telran.calculator.net;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;

import telran.calculator.controller.CalculatorActions;
import telran.calculator.service.Calculator;
import telran.calculator.net.CalculatorProxy;
import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class CalculatorClientAppl {
	private static final int PORT = 2000;
	
	public static void main(String[] args)  { 
		Socket socket;
		BufferedReader reader;
		PrintStream writer;
		
		try {
			socket = new Socket("localhost", PORT);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintStream(socket.getOutputStream());
		} catch (IOException ex) {
			System.out.println("IO Exception: " + ex.toString());
			return;
		}
		Calculator calculator =  new CalculatorProxy(reader, writer);
		ArrayList<Item> items = CalculatorActions.getCalculatorActions(calculator);
		items.add(Item.of("Exit", iop -> {
					try {
						socket.close();
						reader.close();
						writer.close();
					} catch (IOException ex) {
						System.out.println("Socket close Exception: " + ex.toString());
					}
				}, true));
		InputOutput io = new ConsoleInputOutput();
		Menu menu = new Menu("Network Calculator", items);
		menu.perform(io);
	}

}