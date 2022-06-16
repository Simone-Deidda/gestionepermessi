package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.prova.gestionepermessi.dto.RuoloDTO;
import it.prova.gestionepermessi.dto.UtenteDTO;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;

@Controller
@RequestMapping(value = "/utente")
public class UtenteController {
	@Autowired
	private UtenteService utenteService;
	@Autowired
	private RuoloService ruoloService;

	@GetMapping("/search")
	public String searchUtente(ModelMap model) {
		model.addAttribute("ruolo_list_attribute", RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()));
		//model.addAttribute("ruolo_list_attribute", ruoloService.listAll());
		model.addAttribute("search_utente_attr", new UtenteDTO());
		return "utente/search";
	}
	
	@PostMapping("/list")
	public String listUtenti(@ModelAttribute("search_utente_attr") UtenteDTO utenteExample,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, ModelMap model) {

		List<Utente> utenti = utenteService
				.findByExample(utenteExample.buildUtenteModel(true), pageNo, pageSize, sortBy).getContent();

		model.addAttribute("utente_list_attribute", UtenteDTO.createUtenteDTOListFromModelList(utenti));
		return "utente/list";
	}
}
