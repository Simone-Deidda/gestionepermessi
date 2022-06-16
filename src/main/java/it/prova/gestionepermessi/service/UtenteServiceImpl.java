package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

	@Override
	@Transactional(readOnly = true)
	public Page<Utente> findByExample(Utente example, Integer pageNo, Integer pageSize, String sortBy) {
		
		Specification<Utente> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getUsername()))
				predicates
						.add(cb.like(cb.upper(root.get("username")), "%" + example.getUsername().toUpperCase() + "%"));

			if (example.getStato() != null)
				predicates.add(cb.equal(root.get("stato"), example.getStato()));

			if (example.getDateCreated() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dateCreated"), example.getDateCreated()));
			
			if(!example.getRuoli().isEmpty()) {
				predicates.add(root.join("ruoli").in(example.getRuoli()));
			}
			
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return utenteRepository.findAll(specificationCriteria, paging);
	}

	@Override
	@Transactional(readOnly = true)
	public Utente caricaSingoloUtenteConRuoli(Long idUtente) {
		return utenteRepository.findByIdConRuoli(idUtente).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Utente> listAllUtenti() {
		return (List<Utente>) utenteRepository.findAll();
	}

	@Override
	@Transactional
	public void aggiorna(Utente utenteInstance) {
		Utente utenteReloaded = utenteRepository.findById(utenteInstance.getId()).orElse(null);
		if(utenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		utenteReloaded.setRuoli(utenteInstance.getRuoli());
		utenteRepository.save(utenteReloaded);
	}
}
