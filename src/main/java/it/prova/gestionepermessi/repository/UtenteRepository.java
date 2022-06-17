package it.prova.gestionepermessi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
	@EntityGraph(attributePaths = { "ruoli", "dipendente" })
	Optional<Utente> findByUsername(String username);
	
	Page<Utente> findAll(Specification<Utente> specificationCriteria, Pageable paging);
	
	@Query("from Utente u left join fetch u.ruoli where u.id = ?1")
	Optional<Utente> findByIdConRuoli(Long id);
	
}
