package it.prova.gestionepermessi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "attachment")
public class Attachment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	private String nomeFile;
	private String contentType;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "richiestaPermesso_id", referencedColumnName = "id", nullable = true)
	private RichiestaPermesso richiestaPermesso;

	@Lob
	private byte[] payload;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	public RichiestaPermesso getRichiestaPermesso() {
		return richiestaPermesso;
	}

	public void setRichiestaPermesso(RichiestaPermesso richiestaPermesso) {
		this.richiestaPermesso = richiestaPermesso;
	}

}
