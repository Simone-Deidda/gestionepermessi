package it.prova.gestionepermessi.service;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface MessaggioService {

	public void inserisciNuovo(Messaggio messaggio, RichiestaPermesso richiesta);

}
