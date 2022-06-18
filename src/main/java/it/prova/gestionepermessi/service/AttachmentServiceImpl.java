package it.prova.gestionepermessi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.repository.AttachmentRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	@Autowired
	private AttachmentRepository attachmentRepository;

	@Override
	public Attachment cercaPerIdRichiesta(Long idRichiesta) {
		return attachmentRepository.findByIdRichiesta(idRichiesta).orElse(null);
	}

}
