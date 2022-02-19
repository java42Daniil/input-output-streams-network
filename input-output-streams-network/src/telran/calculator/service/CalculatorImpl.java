package telran.calculator.service;
import java.util.*;
import java.util.function.BinaryOperator;
public class CalculatorImpl implements Calculator {
private static HashMap<String, BinaryOperator<Double>> mapOperators;
static {
	
	mapOperators = new HashMap<>();
	mapOperators.put("+", (op1,op2) -> op1+op2);
	mapOperators.put("-", (op1,op2) -> op1-op2);
	mapOperators.put("*", (op1,op2) -> op1*op2);
	mapOperators.put("/", (op1,op2) -> op1/op2);
}

public double compute(String operator, double op1, double op2) {
	BinaryOperator<Double> metod = mapOperators.getOrDefault(operator,
			(a, b) -> {throw new IllegalArgumentException("unknow operator");});
return 	metod.apply(op1, op2);
}
}