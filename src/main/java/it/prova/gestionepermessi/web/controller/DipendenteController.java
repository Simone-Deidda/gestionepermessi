package it.prova.gestionepermessi.web.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;
import it.prova.gestionepermessi.validator.DipendenteValidator;

@Controller
@RequestMapping(value = "/dipendente")
public class DipendenteController {
	@Autowired
	private DipendenteValidator dipendenteValidator;
	@Autowired
	private DipendenteService dipendenteService;
	@Autowired
	private RuoloService ruoloService;
	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public ModelAndView listAllDipendenti() {
		ModelAndView mv = new ModelAndView();
		List<Dipendente> dipendenti = dipendenteService.listAllUtenti();
		mv.addObject("dipendente_list_attribute", dipendenti);
		mv.setViewName("dipendente/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchUtente(ModelMap model) {
		model.addAttribute("search_dipendente_attr", new DipendenteDTO());
		return "dipendente/search";
	}

	@PostMapping("/list")
	public String listUtenti(@ModelAttribute("search_dipendente_attr") DipendenteDTO dipendenteExample,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, ModelMap model) {

		List<Dipendente> utenti = dipendenteService
				.findByExample(dipendenteExample.buildDipendenteModel(), pageNo, pageSize, sortBy).getContent();

		model.addAttribute("dipendente_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(utenti));
		return "dipendente/list";
	}

	@GetMapping("/show/{idDipendente}")
	public String show(@PathVariable(required = true) Long idDipendente, Model model) {
		Dipendente dipendenteModel = dipendenteService.caricaSingoloElemento(idDipendente);
		model.addAttribute("show_dipendente_attr", DipendenteDTO.buildDipendenteDTOFromModel(dipendenteModel));
		return "dipendente/show";
	}

	@GetMapping("/insert")
	public String insertDipendente(Model model) {
		model.addAttribute("insert_dipendente_attr", new DipendenteDTO());
		return "dipendente/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_dipendente_attr") DipendenteDTO dipendenteDTO,
			BindingResult result, RedirectAttributes redirectAttrs) {
		dipendenteValidator.validate(dipendenteDTO.buildDipendenteModel(), result);

		if (result.hasErrors()) {
			return "dipendente/insert";
		}

		Dipendente dipendente = dipendenteDTO.buildDipendenteModel();
		Utente utente = new Utente();
		
		utenteService.inserisciNuovo(utente, dipendente);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/dipendente";
	}

}
