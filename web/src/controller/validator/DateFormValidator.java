package controller.validator;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DateFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> paramClass) {
		return RangoFechas.class.isAssignableFrom(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fechaIni", "fechaIni.required");

		RangoFechas rango = (RangoFechas) obj;
		Date fecIni = rango.getFechaIni();
		Date fecFin = rango.getFechaFin();
		Date when = new Date();
		if(fecFin.after(when)){
			errors.rejectValue("fechaFin", "actualValue", new Object[]{"'fechaFin'"}, "La fecha de fin no puede ser superior a la actual.");
		}
		if(fecIni.after(fecFin)){
			errors.rejectValue("fechaIni", "nullValue", new Object[]{"'fechaIni'"}, "La fecha de inicio no puede ser superior a la de fin.");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fechaFin", "fechaFin.required");
	}

}
