package it.prova.gestionepermessi.dto;

import java.util.ArrayList;
import java.util.List;

import it.prova.gestionepermessi.model.Ruolo;

public class RuoloDTO {

	private Long id;
	private String descrizione;
	private String codice;

	public RuoloDTO(Long id, String descrizione, String codice) {
		this.id = id;
		this.descrizione = descrizione;
		this.codice = codice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public static RuoloDTO buildRuoloDTOFromModel(Ruolo ruoloModel) {
		return new RuoloDTO(ruoloModel.getId(), ruoloModel.getDescrizione(), ruoloModel.getCodice());
	}

	public static List<RuoloDTO> createRuoloDTOListFromModelList(List<Ruolo> listRuoli) {
		List<RuoloDTO> result = new ArrayList<RuoloDTO>();
		for (Ruolo ruolo : listRuoli) {
			result.add(RuoloDTO.buildRuoloDTOFromModel(ruolo));
		}
		return result;
	}

}
