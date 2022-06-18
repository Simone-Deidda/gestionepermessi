package it.prova.gestionepermessi.service;

import it.prova.gestionepermessi.model.Attachment;

public interface AttachmentService {

	public Attachment cercaPerIdRichiesta(Long idRichiesta);

	public void cancella(Attachment attachment);

}
