package model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DNIValidator implements ConstraintValidator<DNI, String> {

	@Override
	public void initialize(DNI paramDNI) {
	}

	@Override
	public boolean isValid(String carnet, ConstraintValidatorContext ctx) {
		if (carnet == null || carnet.equals("")) {
			return true;
		}
//		System.out.println(carnet);
		// validate identification numbers of format "01234567A"
		if (carnet.matches("(\\d{8})([A-Z])") && calcularLetra(Integer.parseInt(carnet.substring(0, 8))) == carnet.charAt(8)) return true;
		// validate identification numbers of format "1234567A"
		else if (carnet.matches("(\\d{7})([A-Z])") && calcularLetra(Integer.parseInt(carnet.substring(0, 7))) == carnet.charAt(7)) return true;
		else return false;
	}

	private static char calcularLetra(int dni){
	    String caracteres="TRWAGMYFPDXBNJZSQVHLCKE";
	    int resto = dni%23;
	    return caracteres.charAt(resto);
	}
}