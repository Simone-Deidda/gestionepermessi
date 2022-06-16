package it.prova.gestionepermessi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.Utente;

public interface UtenteService {

	public Utente cercaPerUsername(String username);

	public void inserisciNuovo(Utente utente);

	public void changeUserAbilitation(Long id);

	public Optional<Utente> caricaSingoloUtente(Long id);

	public Page<Utente> findByExample(Utente example, Integer pageNo, Integer pageSize, String sortBy);
}
