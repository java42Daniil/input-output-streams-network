package telran.calculator.net;

import java.io.BufferedReader;
import java.io.PrintStream;

import telran.calculator.service.Calculator;

public class CalculatorProxy implements Calculator {
	private  PrintStream writer;
	private  BufferedReader reader;

	public CalculatorProxy(BufferedReader reader, PrintStream writer ){		
		this.writer = writer;
		this.reader = reader;
	}

	@Override
	public double compute(String operator, double op1, double op2) {
		writer.println(String.format("%s#%s#%s", operator, Double.toString(op1), Double.toString(op2)));
		try {
			return Double.parseDouble(reader.readLine());
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}
}