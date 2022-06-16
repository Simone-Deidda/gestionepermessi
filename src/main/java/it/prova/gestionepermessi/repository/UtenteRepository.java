package it.prova.gestionepermessi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
	@EntityGraph(attributePaths = { "ruoli", "dipendente" })
	Optional<Utente> findByUsername(String username);
}
