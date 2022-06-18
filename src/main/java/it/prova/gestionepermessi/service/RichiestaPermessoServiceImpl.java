package it.prova.gestionepermessi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.JoinType;
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
import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.AttachmentRepository;
import it.prova.gestionepermessi.repository.RichiestaPermessoRepository;

@Service
public class RichiestaPermessoServiceImpl implements RichiestaPermessoService {
	@Autowired
	private RichiestaPermessoRepository richiestaPermessoRepository;
	@Autowired
	private AttachmentRepository attachmentRepository;
	@Autowired
	private MessaggioService messaggioService;

	@Override
	@Transactional(readOnly = true)
	public List<RichiestaPermesso> listAllRichieste() {
		return (List<RichiestaPermesso>) richiestaPermessoRepository.findAll();
	}

	@Override
	@Transactional
	public void inserisciNuovo(RichiestaPermesso richiesta, Boolean giornoUnico, MultipartFile multipartFile) {
		if (giornoUnico) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(richiesta.getDataInizio());
			calendar.add(Calendar.HOUR, 24);
			richiesta.setDataFine(calendar.getTime());
		}

		richiestaPermessoRepository.save(richiesta);
		if (multipartFile != null) {
			Attachment newfile = new Attachment();
			newfile.setNomeFile(multipartFile.getOriginalFilename());
			newfile.setContentType(multipartFile.getContentType());
			try {
				newfile.setPayload(multipartFile.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			newfile.setRichiestaPermesso(richiesta);
			attachmentRepository.save(newfile);
		}
		messaggioService.inserisciNuovo(new Messaggio(), richiesta);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<RichiestaPermesso> findByExample(RichiestaPermesso example, Integer pageNo, Integer pageSize,
			String sortBy) {
		Specification<RichiestaPermesso> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();
			root.fetch("dipendente", JoinType.INNER);

			if (example.getTipoPermesso() != null)
				predicates.add(cb.equal(cb.upper(root.get("tipoPermesso")), example.getTipoPermesso()));

			if (example.getDataInizio() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataInizio"), example.getDataInizio()));

			if (example.getDataFine() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataFine"), example.getDataFine()));
			
			predicates.add(cb.equal(root.get("approvato"), example.isApprovato()));

			if (StringUtils.isNotEmpty(example.getCodiceCertificato()))
				predicates.add(cb.like(cb.upper(root.get("codiceCertificato")),
						"%" + example.getCodiceCertificato().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getNote()))
				predicates.add(cb.like(cb.upper(root.get("note")), "%" + example.getNote().toUpperCase() + "%"));

			if (example.getDipendente() != null && example.getDipendente().getId() != null) {
				predicates.add(
						cb.equal(root.join("dipendente", JoinType.INNER).get("id"), example.getDipendente().getId()));
			}

			query.distinct(true);
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return richiestaPermessoRepository.findAll(specificationCriteria, paging);
	}

	@Override
	@Transactional(readOnly = true)
	public RichiestaPermesso caricaRichiestaConDipendente(Long id) {
		return richiestaPermessoRepository.findByIdEager(id);
	}

	@Override
	@Transactional
	public void aggiorna(RichiestaPermesso richiestaModel) {
		richiestaPermessoRepository.save(richiestaModel);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RichiestaPermesso> cercaPerIdDipendente(Long id) {
		return richiestaPermessoRepository.findAllByDipendente_Id(id);
	}

	@Override
	@Transactional(readOnly = true)
	public RichiestaPermesso caricaSingolo(Long idRichiesta) {
		return richiestaPermessoRepository.findById(idRichiesta).orElse(null);
	}

	@Override
	@Transactional
	public void cancella(RichiestaPermesso richiestaModel) {
		richiestaPermessoRepository.delete(richiestaModel);
	}

}
