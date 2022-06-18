package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoService {

	public List<RichiestaPermesso> listAllRichieste();

	public void inserisciNuovo(RichiestaPermesso richiesta, Boolean giornoUnico, MultipartFile multipartFile);

	public Page<RichiestaPermesso> findByExample(RichiestaPermesso buildUtenteModel, Integer pageNo, Integer pageSize,
			String sortBy);

	public RichiestaPermesso caricaRichiestaConDipendente(Long idUtente);

	public void aggiorna(RichiestaPermesso richiestaModel);

	public List<RichiestaPermesso> cercaPerIdDipendente(Long id);

}
