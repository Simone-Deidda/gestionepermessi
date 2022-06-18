package it.prova.gestionepermessi.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

import it.prova.gestionepermessi.dto.AttachmentDTO;
import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.dto.RuoloDTO;
import it.prova.gestionepermessi.dto.UtenteDTO;
import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.AttachmentService;
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
	@Autowired
	private AttachmentService attachmentService;

	@GetMapping
	public ModelAndView listAllRichieste() {
		ModelAndView mv = new ModelAndView();
		List<RichiestaPermesso> richieste = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new RuntimeException("Qualcosa è andato storto");
		}
		for (GrantedAuthority ruolo : auth.getAuthorities()) {
			if ("ROLE_DIPENDENTE_USER".equalsIgnoreCase(ruolo.toString())) {
				Dipendente dipendenteInSessione = dipendenteService.cercaPerUsername(auth.getName());
				richieste = richiestaPermessoService.cercaPerIdDipendente(dipendenteInSessione.getId());
			}
		}
		if (richieste == null) {

			richieste = richiestaPermessoService.listAllRichieste();
		}

		mv.addObject("richiestapermesso_list_attribute",
				RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModelList(richieste));
		mv.setViewName("richiestapermesso/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchRichieste(ModelMap model) {
		model.addAttribute("search_richiestapermesso_dipendente_attr",
				DipendenteDTO.createDipendenteDTOListFromModelList(dipendenteService.listAllDipendenti()));
		model.addAttribute("search_richiestapermesso_attr", new RichiestaPermessoDTO());
		return "richiestapermesso/search";
	}

	@PostMapping("/list")
	public String listRichieste(@ModelAttribute("search_richiestapermesso_attr") RichiestaPermessoDTO example,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new RuntimeException("Qualcosa è andato storto");
		}
		for (GrantedAuthority ruolo : auth.getAuthorities()) {
			if ("ROLE_DIPENDENTE_USER".equalsIgnoreCase(ruolo.toString())) {
				Dipendente dipendenteInSessione = dipendenteService.cercaPerUsername(auth.getName());
				example.setDipendenteId(dipendenteInSessione.getId());
			}
		}

		List<RichiestaPermesso> richieste = richiestaPermessoService
				.findByExample(example.buildRichiestaPermessoModel(true), pageNo, pageSize, sortBy).getContent();

		model.addAttribute("richiestapermesso_list_attribute",
				RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModelList(richieste));
		return "richiestapermesso/list";
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
		richiestaPermessoService.inserisciNuovo(richiesta, richiestaDTO.getGiornoSingolo(),
				richiestaDTO.getAttachment());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/richiestapermesso";
	}

	@GetMapping("/show/{idRichiesta}")
	public String show(@PathVariable(required = true) Long idRichiesta, Model model) {
		RichiestaPermesso richiestaModel = richiestaPermessoService.caricaRichiestaConDipendente(idRichiesta);
		Attachment attachment = attachmentService.cercaPerIdRichiesta(idRichiesta);

		model.addAttribute("show_richiestapermesso_attr",
				RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiestaModel));
		model.addAttribute("show_richiestapermesso_dipendente_attr",
				DipendenteDTO.buildDipendenteDTOFromModel(richiestaModel.getDipendente()));
		if (attachment == null) {
			model.addAttribute("show_richiestapermesso_attachment_attr", null);
		} else {
			model.addAttribute("show_richiestapermesso_attachment_attr",
					AttachmentDTO.buildAttachmentDTOFromModel(attachment));

		}

		return "richiestapermesso/show";
	}

	@GetMapping("/cambiaStato/{idRichiesta}")
	public String cambiaStato(@PathVariable(required = true) Long idRichiesta, Model model) {
		RichiestaPermesso richiestaModel = richiestaPermessoService.caricaRichiestaConDipendente(idRichiesta);
		Attachment attachment = attachmentService.cercaPerIdRichiesta(idRichiesta);

		System.out.println(richiestaModel.isApprovato());
		richiestaModel.setApprovato(!richiestaModel.isApprovato());
		richiestaPermessoService.aggiorna(richiestaModel);
		System.out.println(richiestaModel.isApprovato());

		model.addAttribute("show_richiestapermesso_attr",
				RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiestaModel));
		model.addAttribute("show_richiestapermesso_dipendente_attr",
				DipendenteDTO.buildDipendenteDTOFromModel(richiestaModel.getDipendente()));
		model.addAttribute("show_richiestapermesso_attachment_attr",
				AttachmentDTO.buildAttachmentDTOFromModel(attachment));

		System.out.println("return");
		return "richiestapermesso/show";
	}
}
