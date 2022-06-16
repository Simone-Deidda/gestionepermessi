package it.prova.gestionepermessi;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.Sesso;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;

@SpringBootApplication
public class GestionepermessiApplication implements CommandLineRunner {
	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;
	@Autowired
	private DipendenteService dipendenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(GestionepermessiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Back Office User", "ROLE_BO_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Back Office User", "ROLE_BO_USER"));
		}
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Dipendente User", "ROLE_DIPENDENTE_USER"));
		}

		if (utenteServiceInstance.cercaPerUsername("c.manca") == null) {
			Utente admin = new Utente("c.manca", "Password@01", new Date());
			Dipendente dipendente = new Dipendente("Carlo", "Manca", "c.manca@prova.it",
					new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990"), new Date(), Sesso.MASCHIO);
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
			dipendente.setUtente(admin);

			utenteServiceInstance.inserisciNuovo(admin);
			dipendenteServiceInstance.inserisciNuovo(dipendente);
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}
		if (utenteServiceInstance.cercaPerUsername("p.limiti") == null) {
			Utente backOffice = new Utente("p.limiti", "Password@01", new Date());
			Dipendente dipendente = new Dipendente("Paolo", "Limiti", "p.limiti@prova.it",
					new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990"), new Date(), Sesso.MASCHIO);
			backOffice.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Back Office User", "ROLE_BO_USER"));
			dipendente.setUtente(backOffice);

			utenteServiceInstance.inserisciNuovo(backOffice);
			dipendenteServiceInstance.inserisciNuovo(dipendente);
			utenteServiceInstance.changeUserAbilitation(backOffice.getId());
		}
		if (utenteServiceInstance.cercaPerUsername("c.delogu") == null) {
			Utente dip = new Utente("c.delogu", "Password@01", new Date());
			Dipendente dipendente = new Dipendente("Cesira", "Delogu", "c.delogu@prova.it",
					new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990"), new Date(), Sesso.FEMMINA);
			dip.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER"));
			dipendente.setUtente(dip);

			utenteServiceInstance.inserisciNuovo(dip);
			dipendenteServiceInstance.inserisciNuovo(dipendente);
			utenteServiceInstance.changeUserAbilitation(dip.getId());
		}
	}

}
