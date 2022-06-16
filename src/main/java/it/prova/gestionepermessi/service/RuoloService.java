package it.prova.gestionepermessi.service;

import it.prova.gestionepermessi.model.Ruolo;

public interface RuoloService {

	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice);

	public void inserisciNuovo(Ruolo ruolo);

}
