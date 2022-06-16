package it.prova.gestionepermessi.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.UtenteRepository;


@Component
public class CustomAuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Autowired
	private UtenteRepository utenteRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Utente utenteFromDb = utenteRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("Username " + authentication.getName() + " not found"));
		DipendenteDTO dipendenteParziale = new DipendenteDTO();
		dipendenteParziale.setNome(utenteFromDb.getDipendente().getNome());
		dipendenteParziale.setCognome(utenteFromDb.getDipendente().getCognome());
		request.getSession().setAttribute("userInfo", dipendenteParziale);
		response.sendRedirect("home");

	}

}
