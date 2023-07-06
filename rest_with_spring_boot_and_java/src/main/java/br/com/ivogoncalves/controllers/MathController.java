package br.com.ivogoncalves.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.ivogoncalves.exceptions.UnsupportedMathOperationException;
import br.com.ivogoncalves.math.MathOperations;
import br.com.ivogoncalves.utils.ConverterAndCheck;

@RestController
public class MathController {

	//private static final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sum(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo ) throws Exception {
		if(!ConverterAndCheck.isNumeric(numberOne) || !ConverterAndCheck.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
		return MathOperations.sum(numberOne,numberTwo);
	}
	
	@RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double subtraction(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo ) throws Exception {
		if(!ConverterAndCheck.isNumeric(numberOne) || !ConverterAndCheck.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
		return MathOperations.subtraction(numberOne,numberTwo);
	}
	
	@RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double multiplication(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo ) throws Exception {
		if(!ConverterAndCheck.isNumeric(numberOne) || !ConverterAndCheck.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
		return MathOperations.multiplication(numberOne,numberTwo);
	}
	
	@RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double division(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo ) throws Exception {
		if(!ConverterAndCheck.isNumeric(numberOne) || !ConverterAndCheck.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
		return MathOperations.division(numberOne,numberTwo);
	}
	
	@RequestMapping(value = "/average/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double average(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo ) throws Exception {
		if(!ConverterAndCheck.isNumeric(numberOne) || !ConverterAndCheck.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
		return MathOperations.average(numberOne,numberTwo);
	}
	
	@RequestMapping(value = "/sqrt/{number}", method = RequestMethod.GET)
	public Double squareRoot(@PathVariable(value = "number") String number) throws Exception {
		if(!ConverterAndCheck.isNumeric(number)) throw new UnsupportedMathOperationException("Please set a numeric value!");
		return MathOperations.squareRoot(number);
	}
}
