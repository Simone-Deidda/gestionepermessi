package it.prova.gestionepermessi.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.MessaggioRepository;

@Service
public class MessaggioServiceImpl implements MessaggioService {
	@Autowired
	private MessaggioRepository messaggioRepository;

	@Override
	@Transactional
	public void inserisciNuovo(Messaggio messaggio, RichiestaPermesso richiesta) {
		String note = richiesta.getNote().isBlank() ? "" : " , le note del dipendente " + richiesta.getNote();
		String codice = richiesta.getCodiceCertificato().isBlank() ? ""
				: " , il Codice del Certificato: " + richiesta.getCodiceCertificato();
		String attachment = richiesta.getAttachment() == null ? "" : " , il file allegato";
		String ending = ".";

		if (!note.isBlank() || !codice.isBlank() || !attachment.isBlank()) {
			ending += " A seguire trovate:";
			ending += note.isBlank() ? "" : " " + note;
			ending += codice.isBlank() ? "" : " " + codice;
			ending += attachment.isBlank() ? "" : " " + attachment;
			ending += ".";
		}

		messaggio.setOggetto("Richiesta Permesso di " + richiesta.getDipendente().getNome() + " "
				+ richiesta.getDipendente().getCognome());

		messaggio.setTesto(
				"Il dipendente " + richiesta.getDipendente().getNome() + " " + richiesta.getDipendente().getCognome()
						+ " ha richiesto un permesso di tipo " + richiesta.getTipoPermesso() + " a partire del "
						+ richiesta.getDataInizio() + " al " + richiesta.getDataFine() + ending);
		
		messaggio.setDataInserimento(new Date());
		messaggio.setLetto(false);
		messaggio.setRichiestaPermesso(richiesta);
		
		messaggioRepository.save(messaggio);
	}

}
