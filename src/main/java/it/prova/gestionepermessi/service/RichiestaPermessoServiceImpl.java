package it.prova.gestionepermessi.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.AttachmentRepository;
import it.prova.gestionepermessi.repository.RichiestaPermessoRepository;

@Service
public class RichiestaPermessoServiceImpl implements RichiestaPermessoService{
	@Autowired
	private RichiestaPermessoRepository richiestaPermessoRepository;
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<RichiestaPermesso> listAllRichieste() {
		return (List<RichiestaPermesso>) richiestaPermessoRepository.findAll();
	}

	@Override
	@Transactional
	public void inserisciNuovo(RichiestaPermesso richiesta, Boolean giornoUnico, MultipartFile multipartFile) {
		if (giornoUnico) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(richiesta.getDataInizio());
			calendar.add(Calendar.HOUR, 24);
			richiesta.setDataFine(calendar.getTime());
		}
		
		richiestaPermessoRepository.save(richiesta);
		if (multipartFile != null) {
			Attachment newfile = new Attachment();
			newfile.setNomeFile(multipartFile.getOriginalFilename());
			newfile.setContentType(multipartFile.getContentType());
			try {
				newfile.setPayload(multipartFile.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			newfile.setRichiestaPermesso(richiesta);
			attachmentRepository.save(newfile);
		}
		
	}

}
