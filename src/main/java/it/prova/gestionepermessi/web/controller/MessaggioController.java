package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.MessaggioDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.MessaggioService;

@Controller
@RequestMapping(value = "/messaggio")
public class MessaggioController {
	@Autowired
	private MessaggioService messaggioService;
	@Autowired
	private DipendenteService dipendenteService;

	@GetMapping
	public ModelAndView listAllMessaggi() {
		ModelAndView mv = new ModelAndView();
		List<Messaggio> messaggi = messaggioService.listAllMessaggi();
		mv.addObject("messaggio_list_attribute", messaggi);
		mv.setViewName("messaggio/list");
		return mv;
	}
	
	@GetMapping("/search")
	public String search(ModelMap model) {
		model.addAttribute("search_messaggio_attr", new MessaggioDTO());
		return "messaggio/search";
	}
	
	@PostMapping("/list")
	public String listRichieste(@ModelAttribute("search_messaggio_attr") MessaggioDTO example,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, ModelMap model) {

		List<Messaggio> richieste = messaggioService
				.findByExample(example.buildMessaggioModel(), pageNo, pageSize, sortBy).getContent();

		model.addAttribute("messaggio_list_attribute",
				MessaggioDTO.buildMessaggioDTOFromModelList(richieste));
		return "messaggio/list";
	}

}
