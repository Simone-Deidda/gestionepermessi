package it.prova.gestionepermessi.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.prova.gestionepermessi.model.Dipendente;

@Component
public class DipendenteValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Dipendente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Dipendente dipendente = (Dipendente) target;

		if (dipendente.getDataAssunzione() != null && dipendente.getDataDimissioni() != null
				&& (dipendente.getDataAssunzione().before(dipendente.getDataNascita())
						|| dipendente.getDataDimissioni().before(dipendente.getDataNascita()))) {
			errors.rejectValue("dataNascita", "dataNascita.after.altreDate");
		}
		if (dipendente.getDataAssunzione() == null && dipendente.getDataDimissioni() != null) {
			errors.rejectValue("dataAssunzione", "dataAssunzione.after.dataDimissioni");
		}
		if (dipendente.getDataAssunzione() != null
				&& dipendente.getDataAssunzione().before(dipendente.getDataDimissioni())) {
			errors.rejectValue("dataAssunzione", "dataAssunzione.after.dataDimissioni");
		}
	}

}
