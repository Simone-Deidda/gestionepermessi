package it.prova.gestionepermessi.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {
	@Autowired
	private UtenteRepository utenteRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public Utente cercaPerUsername(String username) {
		return utenteRepository.findByUsername(username).orElse(null);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Utente utente) {
		utente.setPassword(passwordEncoder.encode(utente.getPassword())); 
		utente.setDateCreated(new Date());
		utenteRepository.save(utente);
	}

	@Override
	@Transactional
	public void changeUserAbilitation(Long id) {
		Utente utenteInstance = caricaSingoloUtente(id).orElse(null);
		if(utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");
		
		if(utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if(utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if(utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Utente> caricaSingoloUtente(Long id) {
		return utenteRepository.findById(id);
	}

}
