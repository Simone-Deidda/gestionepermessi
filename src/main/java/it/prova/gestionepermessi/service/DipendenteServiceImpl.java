package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.DipendenteRepository;
import it.prova.gestionepermessi.repository.UtenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService {
	@Autowired
	private DipendenteRepository dipendenteRepository;
	@Autowired
	private UtenteRepository utenteRepository;

	@Override
	public void inserisciNuovo(Dipendente dipendente) {
		dipendenteRepository.save(dipendente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Dipendente> listAllUtenti() {
		return (List<Dipendente>) dipendenteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Dipendente> findByExample(Dipendente example, Integer pageNo, Integer pageSize, String sortBy) {
		Specification<Utente> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getNome()))
				predicates.add(cb.like(cb.upper(root.get("nome")), "%" + example.getNome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCognome()))
				predicates.add(cb.like(cb.upper(root.get("cognome")), "%" + example.getCognome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCodiceFiscale()))
				predicates.add(cb.like(cb.upper(root.get("codiceFiscale")),
						"%" + example.getCodiceFiscale().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getEmail()))
				predicates.add(cb.like(cb.upper(root.get("email")), "%" + example.getEmail().toUpperCase() + "%"));

			if (example.getSesso() != null)
				predicates.add(cb.equal(root.get("sesso"), example.getSesso()));

			if (example.getDataNascita() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataNascita"), example.getDataNascita()));

			if (example.getDataAssunzione() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataAssunzione"), example.getDataAssunzione()));

			if (example.getDataDimissioni() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataDimissioni"), example.getDataDimissioni()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return dipendenteRepository.findAll(specificationCriteria, paging);
	}

	@Override
	@Transactional(readOnly = true)
	public Dipendente caricaSingoloElemento(Long idDipendente) {
		return dipendenteRepository.findById(idDipendente).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Dipendente toBeUpdated) {
		Dipendente dipendenteFromDB = dipendenteRepository.findByIdConUtente(toBeUpdated.getId()).orElse(null);
		if (dipendenteFromDB == null || dipendenteFromDB.getUtente() == null) {
			throw new RuntimeException("Qualcosa è andato storto.");
		}
		
		toBeUpdated.setUtente(dipendenteFromDB.getUtente());
		toBeUpdated.getUtente().setUsername(toBeUpdated.getNome().toLowerCase().charAt(0) + "." + toBeUpdated.getCognome().toLowerCase());
		toBeUpdated.setEmail(toBeUpdated.getUtente().getUsername() + "@prova.it");
		
		utenteRepository.save(toBeUpdated.getUtente());
		dipendenteRepository.save(toBeUpdated);
	}

	@Override
	@Transactional(readOnly = true)
	public Dipendente cercaPerUsername(String username) {
		return dipendenteRepository.findByUsername(username).orElse(null);
	}

}
