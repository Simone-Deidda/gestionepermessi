package it.prova.gestionepermessi.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Sesso;

public class DipendenteDTO {
	private Long id;
	@NotBlank(message = "{nome.notblank}")
	private String nome;
	@NotBlank(message = "{cognome.notblank}")
	private String cognome;
	private String codiceFiscale;
	private String email;
	@NotNull(message = "{dataDiNascita.notnull}")
	private Date dataNascita;
	private Date dataAssunzione;
	private Date dataDimissioni;
	@NotNull(message = "{sesso.notnull}")
	private Sesso sesso;
	private UtenteDTO utente;

	public DipendenteDTO() {
	}

	public DipendenteDTO(Long id, String nome, String cognome, String codiceFiscale, String email, Date dataNascita,
			Date dataAssunzione, Date dataDimissioni, Sesso sesso) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.dataDimissioni = dataDimissioni;
		this.sesso = sesso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataAssunzione() {
		return dataAssunzione;
	}

	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}

	public Date getDataDimissioni() {
		return dataDimissioni;
	}

	public void setDataDimissioni(Date dataDimissioni) {
		this.dataDimissioni = dataDimissioni;
	}

	public Sesso getSesso() {
		return sesso;
	}

	public void setSesso(Sesso sesso) {
		this.sesso = sesso;
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}

	public static DipendenteDTO buildDipendenteDTOFromModel(Dipendente dipendenteModel) {
		return new DipendenteDTO(dipendenteModel.getId(), dipendenteModel.getNome(), dipendenteModel.getCognome(),
				dipendenteModel.getCodiceFiscale(), dipendenteModel.getEmail(), dipendenteModel.getDataNascita(),
				dipendenteModel.getDataAssunzione(), dipendenteModel.getDataDimissioni(), dipendenteModel.getSesso());
	}

	public Dipendente buildDipendenteModel() {
		return new Dipendente(this.nome, this.cognome, this.email, this.codiceFiscale, this.dataNascita,
				this.dataAssunzione, this.sesso);
	}

	public static List<DipendenteDTO> createDipendenteDTOListFromModelList(List<Dipendente> modelListInput) {
		return modelListInput.stream().map(utenteEntity -> {
			return DipendenteDTO.buildDipendenteDTOFromModel(utenteEntity);
		}).collect(Collectors.toList());
	}
}
