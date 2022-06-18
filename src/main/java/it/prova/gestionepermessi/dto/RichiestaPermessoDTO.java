package it.prova.gestionepermessi.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.TipoPermesso;

public class RichiestaPermessoDTO {
	private Long id;
	@NotNull(message = "{dataInizio.notnull}")
	private Date dataInizio;
	private Date dataFine;
	private boolean approvato;
	private String codiceCertificato;
	private String note;
	@NotNull(message = "{tipoPermesso.notnull}")
	private TipoPermesso tipoPermesso;
	private MultipartFile attachment;
	private Boolean giornoSingolo;
	private Long dipendenteId;

	public RichiestaPermessoDTO() {

	}

	public RichiestaPermessoDTO(Long id, Date dataInizio, Date dataFine, boolean approvato, String codiceCertificato,
			String note, TipoPermesso tipoPermesso) {
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
		this.tipoPermesso = tipoPermesso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public boolean isApprovato() {
		return approvato;
	}

	public void setApprovato(boolean approvato) {
		this.approvato = approvato;
	}

	public String getCodiceCertificato() {
		return codiceCertificato;
	}

	public void setCodiceCertificato(String codiceCertificato) {
		this.codiceCertificato = codiceCertificato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public TipoPermesso getTipoPermesso() {
		return tipoPermesso;
	}

	public void setTipoPermesso(TipoPermesso tipoPermesso) {
		this.tipoPermesso = tipoPermesso;
	}

	public MultipartFile getAttachment() {
		return attachment;
	}

	public void setAttachment(MultipartFile attachment) {
		this.attachment = attachment;
	}

	public Boolean getGiornoSingolo() {
		return giornoSingolo;
	}

	public void setGiornoSingolo(Boolean giornoSingolo) {
		this.giornoSingolo = giornoSingolo;
	}

	public Long getDipendenteId() {
		return dipendenteId;
	}

	public void setDipendenteId(Long dipendentiIds) {
		this.dipendenteId = dipendentiIds;
	}

	public static RichiestaPermessoDTO buildRichiestaPermessoDTOFromModel(RichiestaPermesso richiestaPermessoModel) {
		return new RichiestaPermessoDTO(richiestaPermessoModel.getId(), richiestaPermessoModel.getDataInizio(),
				richiestaPermessoModel.getDataFine(), richiestaPermessoModel.isApprovato(),
				richiestaPermessoModel.getCodiceCertificato(), richiestaPermessoModel.getNote(),
				richiestaPermessoModel.getTipoPermesso());
	}

	public RichiestaPermesso buildRichiestaPermessoFromModel() {
		return new RichiestaPermesso(this.id, this.dataInizio, this.dataFine, false, this.codiceCertificato, this.note,
				this.tipoPermesso);
	}

	public static List<RichiestaPermessoDTO> buildRichiestaPermessoDTOFromModelList(List<RichiestaPermesso> richieste) {
		return richieste.stream().map(richiesta -> RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiesta))
				.collect(Collectors.toList());
	}

	public RichiestaPermesso buildRichiestaPermessoModel(boolean includesDipendente) {
		RichiestaPermesso result = new RichiestaPermesso(this.id, this.dataInizio, this.dataFine, this.approvato,
				this.codiceCertificato, this.note, this.tipoPermesso);
		if (includesDipendente && dipendenteId != null) {
			result.setDipendente(new Dipendente(this.dipendenteId));
		}
		return result;
	}

}
