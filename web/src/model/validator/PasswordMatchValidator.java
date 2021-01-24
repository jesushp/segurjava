package model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {
	private String field;
    private String fieldMatch;

    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if ( value == null ) {
            return false;
        }

        boolean isValid;
    	
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        
        if (fieldValue != null) {
            System.out.println("Clave: " + fieldValue + " =? " + fieldMatchValue);
             isValid = fieldValue.equals(fieldMatchValue);
        } else {
            isValid = fieldMatchValue == null;
        }

        if(!isValid)
            context.disableDefaultConstraintViolation();
        	context.buildConstraintViolationWithTemplate("{PasswordMatch}").addPropertyNode(fieldMatch).addConstraintViolation();
        return isValid;
    }

}
