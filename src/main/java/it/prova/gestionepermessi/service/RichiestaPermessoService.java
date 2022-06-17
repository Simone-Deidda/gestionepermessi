package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoService {

	List<RichiestaPermesso> listAllRichieste();

	void inserisciNuovo(RichiestaPermesso richiesta, Boolean giornoUnico, MultipartFile multipartFile);

}
