package it.prova.gestionepermessi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Messaggio;

public interface MessaggioRepository extends CrudRepository<Messaggio, Long> {

	Page<Messaggio> findAll(Specification<Messaggio> specificationCriteria, Pageable paging);

	@Query("select m from Messaggio m join m.richiestaPermesso rp join rp.dipendente where m.id = ?1")
	Optional<Messaggio> findAllByIdEager(Long id);

}
