package it.prova.gestionepermessi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "richiestaPermesso")
public class RichiestaPermesso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "dataInizio")
	private Date dataInizio;
	@Column(name = "dataFine")
	private Date dataFine;
	@Column(name = "approvato")
	private boolean approvato;
	@Column(name = "codiceCertificato")
	private String codiceCertificato;
	@Column(name = "note")
	private String note;
	@Enumerated(EnumType.STRING)
	private TipoPermesso tipoPermesso;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "attachement_id")
	private Attachment attachment;
	
	@OneToOne(mappedBy = "richiestaPermesso")
	private Messaggio messaggio;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "dipendente_id")
	private Dipendente dipendente;

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

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public Dipendente getDipendente() {
		return dipendente;
	}

	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}
	
	
}
