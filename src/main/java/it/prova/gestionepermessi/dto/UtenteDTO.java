package it.prova.gestionepermessi.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;

public class UtenteDTO {
	private Long id;
	@NotBlank(message = "{username.notblank}")
	private String username;
	@NotBlank(message = "{password.notblank}")
	private String password;
	private String confirmPassword;
	private Date dateCreated;
	private StatoUtente stato;
	private Long[] ruoliIds;

	public UtenteDTO() {
	}

	public UtenteDTO(Long id, String username, Date dateCreated, StatoUtente stato) {
		this.id = id;
		this.username = username;
		this.dateCreated = dateCreated;
		this.stato = stato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public Long[] getRuoliIds() {
		return ruoliIds;
	}

	public void setRuoliIds(Long[] ruoliIds) {
		this.ruoliIds = ruoliIds;
	}

	public static UtenteDTO buildUtenteDTOFromModel(Utente utenteModel) {
		return new UtenteDTO(utenteModel.getId(), utenteModel.getUsername(), utenteModel.getDateCreated(),
				utenteModel.getStato());
	}

	public boolean isAttivo() {
		return this.stato != null && this.stato.equals(StatoUtente.ATTIVO);
	}

	public boolean isDisabilitato() {
		return this.stato != null && this.stato.equals(StatoUtente.DISABILITATO);
	}

	public Utente buildUtenteModel(boolean includeIdRoles) {
		Utente result = new Utente(this.id, this.username, this.password, this.dateCreated,
				this.stato);
		if (includeIdRoles && ruoliIds != null)
			result.setRuoli(Arrays.asList(ruoliIds).stream().map(id -> new Ruolo(id)).collect(Collectors.toSet()));

		return result;
	}

	public static List<UtenteDTO> createUtenteDTOListFromModelList(List<Utente> modelListInput) {
		return modelListInput.stream().map(utenteEntity -> {
			return UtenteDTO.buildUtenteDTOFromModel(utenteEntity);
		}).collect(Collectors.toList());
	}

}
