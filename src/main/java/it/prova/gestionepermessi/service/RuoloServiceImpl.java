package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.repository.RuoloRepository;

@Service
public class RuoloServiceImpl implements RuoloService {
	@Autowired
	private RuoloRepository ruoloRepository;

	@Override
	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice) {
		return ruoloRepository.findByDescrizioneAndCodice(descrizione, codice);
	}

	@Override
	public void inserisciNuovo(Ruolo ruolo) {
		ruoloRepository.save(ruolo);
	}

	@Override
	public List<Ruolo> listAll() {
		return (List<Ruolo>) ruoloRepository.findAll();
	}

}
