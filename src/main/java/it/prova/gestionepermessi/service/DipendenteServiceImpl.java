package it.prova.gestionepermessi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.repository.DipendenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService {
	@Autowired
	private DipendenteRepository dipendenteRepository;

	@Override
	public void inserisciNuovo(Dipendente dipendente) {
		dipendenteRepository.save(dipendente);
	}

}
