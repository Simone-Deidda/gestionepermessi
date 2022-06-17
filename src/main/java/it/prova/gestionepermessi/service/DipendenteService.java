package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.Dipendente;

public interface DipendenteService {

	public void inserisciNuovo(Dipendente dipendente);

	public List<Dipendente> listAllUtenti();

	public Page<Dipendente> findByExample(Dipendente buildUtenteModel, Integer pageNo, Integer pageSize, String sortBy);

	public Dipendente caricaSingoloElemento(Long idDipendente);

	public void aggiorna(Dipendente buildDipendenteModel);

	public Dipendente cercaPerUsername(String name);

}
