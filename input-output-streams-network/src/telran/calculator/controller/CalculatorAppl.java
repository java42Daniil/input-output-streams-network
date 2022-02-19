package telran.calculator.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;

import telran.calculator.net.CalculatorProxy;
import telran.calculator.service.Calculator;
import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class CalculatorAppl {
	private static final int PORT = 2000;

	public static void main(String[] args) throws Exception {
		InputOutput io = new ConsoleInputOutput();
		Socket socket = new Socket("localhost", PORT);// added line
		try (PrintStream writer = new PrintStream(socket.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			Calculator calculator = new CalculatorProxy(reader, writer);
			ArrayList<Item> items = CalculatorActions.getCalculatorActions(calculator);
			items.add(Item.of("Exit", iop -> {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}, true));
			Menu menu = new Menu("Calculator menu", items);
			menu.perform(io);
		}
	}
}