package it.prova.gestionepermessi.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.model.TipoPermesso;

@Component
public class RichiestaPermessoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RichiestaPermessoDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RichiestaPermessoDTO richiestaPermessoDTO = (RichiestaPermessoDTO) target;

		if (errors.hasErrors()) {
			return;
		}

		if (richiestaPermessoDTO.getDataFine() != null
				&& richiestaPermessoDTO.getDataInizio().after(richiestaPermessoDTO.getDataFine())) {
			errors.rejectValue("dataInizio", "dataInizio.after.dataFine");
		}
		if (!richiestaPermessoDTO.getGiornoSingolo() && richiestaPermessoDTO.getDataFine() == null) {
			errors.rejectValue("dataFine", "dataFine.notnull");
		}
		if (richiestaPermessoDTO.getTipoPermesso() == TipoPermesso.MALATTIA
				&& richiestaPermessoDTO.getCodiceCertificato() == null) {
			errors.rejectValue("codiceCertificato", "codiceCertificato.not.exists.on.MALATTIA");
		}
	}

}
