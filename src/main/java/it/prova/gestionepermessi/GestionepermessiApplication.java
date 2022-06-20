package it.prova.gestionepermessi;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.Sesso;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;

@SpringBootApplication
public class GestionepermessiApplication implements CommandLineRunner {
	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;

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
			Utente admin = new Utente();

			utenteServiceInstance.inserisciNuovo(admin,
					new Dipendente("Carlo", "Manca", new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990"),
							Sesso.MASCHIO),
					ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}
		if (utenteServiceInstance.cercaPerUsername("p.limiti") == null) {
			Utente backOffice = new Utente();

			utenteServiceInstance.inserisciNuovo(backOffice,
					new Dipendente("Paolo", "Limiti", new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990"),
							Sesso.MASCHIO),
					ruoloServiceInstance.cercaPerDescrizioneECodice("Back Office User", "ROLE_BO_USER"));
			utenteServiceInstance.changeUserAbilitation(backOffice.getId());
		}
		if (utenteServiceInstance.cercaPerUsername("c.delogu") == null) {
			Utente dipendente = new Utente();

			utenteServiceInstance.inserisciNuovo(dipendente,
					new Dipendente("Cesira", "Delogu", new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990"),
							Sesso.FEMMINA),
					ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER"));
			utenteServiceInstance.changeUserAbilitation(dipendente.getId());
		}
	}

}
