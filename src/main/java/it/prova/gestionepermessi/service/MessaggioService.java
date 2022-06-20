package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface MessaggioService {

	public void inserisciNuovo(Messaggio messaggio, RichiestaPermesso richiesta);

	public List<Messaggio> listAllMessaggi();

	public Page<Messaggio> findByExample(Messaggio buildMessaggioModel, Integer pageNo, Integer pageSize, String sortBy);

	public Messaggio caricaMessaggioConRichiestaEDipendente(Long idMessaggio);

	public Long contaMessaggiNonLetti();

	public List<Messaggio> listAllMessaggiNonLetti();

	public Messaggio findByRichiesta(Long idRichiesta);

	public void cancella(Messaggio messaggio);

	public void aggiorna(RichiestaPermesso richiestaModel, Messaggio findByRichiesta);

}
