package it.uniroma3.siw.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Opera;

@Component
public class OperaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Opera.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Opera opera = (Opera) target;
		Integer anno = opera.getAnno();
		if(anno == null || anno < 0 || anno > 2022)
			errors.rejectValue("anno", "size");
	}

}
