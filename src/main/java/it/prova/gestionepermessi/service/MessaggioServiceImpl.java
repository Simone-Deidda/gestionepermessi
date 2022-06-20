package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
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

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.MessaggioRepository;

@Service
public class MessaggioServiceImpl implements MessaggioService {
	@Autowired
	private MessaggioRepository messaggioRepository;

	@Override
	@Transactional
	public void inserisciNuovo(Messaggio messaggio, RichiestaPermesso richiesta) {
		String note = richiesta.getNote().isBlank() ? "" : " le note del dipendente " + richiesta.getNote();
		String codice = richiesta.getCodiceCertificato().isBlank() ? ""
				: " il Codice del Certificato: " + richiesta.getCodiceCertificato();
		String attachment = richiesta.getAttachment() == null ? "" : " il file allegato";
		String ending = ".";

		if (!note.isBlank() || !codice.isBlank() || !attachment.isBlank()) {
			ending += " A seguire trovate:";
			ending += note.isBlank() ? "" : " " + note;
			ending += codice.isBlank() ? "" : " " + codice;
			ending += attachment.isBlank() ? "" : " " + attachment;
			ending += ".";
		}

		messaggio.setOggetto("Richiesta Permesso di " + richiesta.getDipendente().getNome() + " "
				+ richiesta.getDipendente().getCognome());

		messaggio.setTesto(
				"Il dipendente " + richiesta.getDipendente().getNome() + " " + richiesta.getDipendente().getCognome()
						+ " ha richiesto un permesso di tipo " + richiesta.getTipoPermesso() + " a partire del "
						+ richiesta.getDataInizio() + " al " + richiesta.getDataFine() + ending);

		messaggio.setDataInserimento(new Date());
		messaggio.setLetto(false);
		messaggio.setRichiestaPermesso(richiesta);

		messaggioRepository.save(messaggio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Messaggio> listAllMessaggi() {
		return (List<Messaggio>) messaggioRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Messaggio> findByExample(Messaggio example, Integer pageNo, Integer pageSize, String sortBy) {
		Specification<Messaggio> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();
			root.fetch("richiestaPermesso", JoinType.INNER);

			if (example.getDataInserimento() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataInserimento"), example.getDataInserimento()));

			if (example.getDataLettura() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataLettura"), example.getDataLettura()));

			predicates.add(cb.equal(root.get("letto"), example.isLetto()));

			if (StringUtils.isNotEmpty(example.getOggetto()))
				predicates.add(cb.like(cb.upper(root.get("oggetto")), "%" + example.getOggetto().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getTesto()))
				predicates.add(cb.like(cb.upper(root.get("testo")), "%" + example.getTesto().toUpperCase() + "%"));

			query.distinct(true);
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return messaggioRepository.findAll(specificationCriteria, paging);
	}

	@Override
	@Transactional(readOnly = true)
	public Messaggio caricaMessaggioConRichiestaEDipendente(Long idMessaggio) {
		Messaggio mess = messaggioRepository.findAllByIdEager(idMessaggio).orElse(null);
		if (mess == null) {
			throw new RuntimeException("Qualcosa è andato storto nel service");
		}
		if (!mess.isLetto()) {
			mess.setLetto(true);
			mess.setDataLettura(new Date());
			messaggioRepository.save(mess);
			
		}
		
		return mess;
	}

	@Override
	@Transactional(readOnly = true)
	public Long contaMessaggiNonLetti() {
		return messaggioRepository.countByLetto(false);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Messaggio> listAllMessaggiNonLetti() {
		return messaggioRepository.findAllByLettoIs(false);
	}

	@Override
	@Transactional(readOnly = true)
	public Messaggio findByRichiesta(Long idRichiesta) {
		return messaggioRepository.findByRichiestaPermesso_Id(idRichiesta);
	}

	@Override
	@Transactional
	public void cancella(Messaggio messaggio) {
		messaggioRepository.delete(messaggio);
	}

	@Override
	@Transactional
	public void aggiorna(RichiestaPermesso richiesta, Messaggio messaggio) {
		String note = richiesta.getNote().isBlank() ? "" : " le note del dipendente " + richiesta.getNote();
		String codice = richiesta.getCodiceCertificato().isBlank() ? ""
				: " il Codice del Certificato: " + richiesta.getCodiceCertificato();
		String attachment = richiesta.getAttachment() == null ? "" : " il file allegato";
		String ending = ".";

		if (!note.isBlank() || !codice.isBlank() || !attachment.isBlank()) {
			ending += " A seguire trovate:";
			ending += note.isBlank() ? "" : " " + note;
			ending += codice.isBlank() ? "" : " " + codice;
			ending += attachment.isBlank() ? "" : " " + attachment;
			ending += ".";
		}

		messaggio.setOggetto("Richiesta Permesso di " + richiesta.getDipendente().getNome() + " "
				+ richiesta.getDipendente().getCognome());

		messaggio.setTesto(
				"Il dipendente " + richiesta.getDipendente().getNome() + " " + richiesta.getDipendente().getCognome()
						+ " ha richiesto un permesso di tipo " + richiesta.getTipoPermesso() + " a partire del "
						+ richiesta.getDataInizio() + " al " + richiesta.getDataFine() + ending);

		messaggio.setDataInserimento(new Date());
		messaggio.setLetto(false);
		messaggio.setRichiestaPermesso(richiesta);

		messaggioRepository.save(messaggio);
	}

}
