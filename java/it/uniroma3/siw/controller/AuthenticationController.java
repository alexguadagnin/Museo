package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.controller.validator.CredenzialiValidator;
import it.uniroma3.siw.controller.validator.UtenteValidator;
import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.CollezioneService;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.CuratoreService;
import it.uniroma3.siw.service.OperaService;

@Controller
public class AuthenticationController {

	@Autowired
	private UtenteValidator utenteValidator;

	@Autowired
	private CredenzialiValidator credenzialiValidator;

	@Autowired
	private CredenzialiService credenzialiService;

	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private ArtistaService artistaService;

	@Autowired
	private CuratoreService curatoreService;

	@Autowired
	private OperaService operaService;

	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String showLoginForm (Model model) {
		model.addAttribute("credenziali", new Credenziali());
		return "loginForm";
	}

	@RequestMapping(value = "/login-failure", method = RequestMethod.POST) 
	public String showLoginFormError (@ModelAttribute("credenziali") Credenziali credenziali, BindingResult credenzialiBindingResult, Model model) {
		credenzialiBindingResult.reject("credenziali.notFound");
		model.addAttribute("credenziali", credenziali);
		return "loginForm";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "index";
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.GET) 
	public String toRegister(Model model) {
		model.addAttribute("utente", new Utente());
		model.addAttribute("credenziali", new Credenziali());
		return "registerUser";
	}

	//problema credenziali
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST) 
	public String register(@ModelAttribute("utente") Utente utente, BindingResult utenteBindingResult, @ModelAttribute("credenziali") Credenziali credenziali, BindingResult credenzialiBindingResult, Model model) {

		this.utenteValidator.validate(utente, utenteBindingResult);
		this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);

		if(!(utenteBindingResult.hasErrors()) && !(credenzialiBindingResult.hasErrors())) {
			credenziali.setUtente(utente);
			credenzialiService.saveCredenziali(credenziali);
			return "loginForm";
		}

		return "registerUser";
	}

	//DA FARE
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String successfulLogin(Model model) {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
		model.addAttribute("utente", credenziali.getUtente());
		model.addAttribute("credenziali", credenziali);
		if(credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
			model.addAttribute("collezioni", collezioneService.getAll());
			model.addAttribute("opere", operaService.getAll());
			return "admin/home";
		}	

		//aggiungiamo in model tutto ciò che serve per la scelta della visualizzazione
		model.addAttribute("autoriVisualizzabili", artistaService.getAll());
		model.addAttribute("curatoriVisualizzabili", curatoreService.getAll());
		model.addAttribute("anniVisualizzabili", operaService.getAllAnni());

		return "home";
	}

}
