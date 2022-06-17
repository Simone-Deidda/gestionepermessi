package it.prova.gestionepermessi.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RichiestaPermessoService;
import it.prova.gestionepermessi.validator.RichiestaPermessoValidator;

@Controller
@RequestMapping(value = "/richiestapermesso")
public class RichiestaPermessoController {
	@Autowired
	private RichiestaPermessoValidator richiestaPermessoValidator;
	@Autowired
	private RichiestaPermessoService richiestaPermessoService;
	@Autowired
	private DipendenteService dipendenteService;
	
	@GetMapping
	public ModelAndView listAllRichieste() {
		ModelAndView mv = new ModelAndView();
		List<RichiestaPermesso> richieste = richiestaPermessoService.listAllRichieste();
		mv.addObject("richiestapermesso_list_attribute", RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModelList(richieste));
		mv.setViewName("richiestapermesso/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchRichieste(ModelMap model) {
		model.addAttribute("search_richiestapermesso_attr", new RichiestaPermessoDTO());
		return "richiestapermesso/search";
	}
	
	@GetMapping("/insert")
	public String insertRichiesta(Model model) {
		model.addAttribute("insert_richiestapermesso_attr", new RichiestaPermessoDTO());
		return "richiestapermesso/insert";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_richiestapermesso_attr") RichiestaPermessoDTO richiestaDTO, 
			BindingResult result, RedirectAttributes redirectAttrs) {
		richiestaPermessoValidator.validate(richiestaDTO, result);
		
		if (result.hasErrors()) {
			return "richiestapermesso/insert";
		}
		RichiestaPermesso richiesta = richiestaDTO.buildRichiestaPermessoFromModel();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth == null) {
			throw new RuntimeException("Qualcosa è andato storto");
		}
		Dipendente dipendenteInsessione = dipendenteService.cercaPerUsername(auth.getName());
		if (dipendenteInsessione == null) {
			throw new RuntimeException("Qualcosa è andato storto");
		}
		
		richiesta.setDipendente(dipendenteInsessione);
		richiestaPermessoService.inserisciNuovo(richiesta, richiestaDTO.getGiornoSingolo(), richiestaDTO.getAttachment());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/richiestapermesso";
	}
}
