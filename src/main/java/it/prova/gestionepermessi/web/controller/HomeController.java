package it.prova.gestionepermessi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prova.gestionepermessi.service.MessaggioService;

@Controller
public class HomeController {
	@Autowired
	private MessaggioService messaggioService;
	
	@RequestMapping(value = {"/home",""})
	public String home(Model model) {
		Long messaggiNonLetti = messaggioService.contaMessaggiNonLetti();
		model.addAttribute("numeroMessaggi", messaggiNonLetti);
		return "index";
	}
}
