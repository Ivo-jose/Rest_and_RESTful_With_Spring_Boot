package br.com.ivogoncalves.math;

import br.com.ivogoncalves.utils.ConverterAndCheck;

public class MathOperations {

	public static Double sum(String numberOne, String numberTwo) {
		return ConverterAndCheck.convertDouble(numberOne) + ConverterAndCheck.convertDouble(numberTwo);
	}

	public static Double subtraction(String numberOne, String numberTwo) {
		return ConverterAndCheck.convertDouble(numberOne) - ConverterAndCheck.convertDouble(numberTwo);
	}

	public static Double multiplication(String numberOne, String numberTwo) {
		return ConverterAndCheck.convertDouble(numberOne) * ConverterAndCheck.convertDouble(numberTwo);
	}

	public static Double division(String numberOne, String numberTwo) {
		return ConverterAndCheck.convertDouble(numberOne) / ConverterAndCheck.convertDouble(numberTwo);
	}

	public static Double average(String numberOne, String numberTwo) {
		return (ConverterAndCheck.convertDouble(numberOne) + ConverterAndCheck.convertDouble(numberTwo)) / 2;
	}

	public static Double squareRoot(String number) {
		return Math.sqrt(ConverterAndCheck.convertDouble(number));
	}
}