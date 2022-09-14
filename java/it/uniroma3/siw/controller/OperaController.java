package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.controller.validator.CredenzialiValidator;
import it.uniroma3.siw.controller.validator.OperaValidator;
import it.uniroma3.siw.model.Collezione;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.service.CollezioneService;
import it.uniroma3.siw.service.OperaService;

@Controller
public class OperaController {

	@Autowired
	private OperaService operaService;
	
	@Autowired
	private OperaValidator operaValidator;
	
	@Autowired
	private CollezioneService collezioneService;
	
	@RequestMapping(value = "/admin/modificaOpera/{id}", method = RequestMethod.GET) 
	public String toModificaOpera (@PathVariable("id") Long id, Model model) {
		model.addAttribute("opera", operaService.getById(id));
		return "admin/modificaOpera";
	}
	
	@RequestMapping(value = "/admin/modificaOpera/{id}", method = RequestMethod.POST) 
	public String modificaOpera (@PathVariable("id") Long id, @ModelAttribute("opera") Opera opera, BindingResult operaBindingResult, Model model) {
		//controlli
		operaValidator.validate(opera, operaBindingResult);
		if(operaBindingResult.hasErrors()) {			
			model.addAttribute("statoModificaInsuccesso", "insuccesso");
			model.addAttribute("opera", operaService.getById(id));
			return "admin/modificaOpera";
		}
		//modifica
		Opera operaDaModificare = operaService.getById(id);
		operaDaModificare.setAnno(opera.getAnno());
		operaDaModificare.setTitolo(opera.getTitolo());
		operaDaModificare.setDescrizione(opera.getDescrizione());
		operaService.save(operaDaModificare);
		
		//success
		model.addAttribute("collezioni", collezioneService.getAll());
		model.addAttribute("opere", operaService.getAll());
		model.addAttribute("statoModificaSuccesso", "successo");
		return "admin/home";
	}
	
	@RequestMapping(value = "/admin/eliminaOpera/{id}", method = RequestMethod.GET) 
	public String toEliminaOpera (@PathVariable("id") Long id, Model model) {
		operaService.deleteById(id);
		
		model.addAttribute("collezioni", collezioneService.getAll());
		model.addAttribute("opere", operaService.getAll());
		model.addAttribute("statoCancellazioneSuccesso", "successo");
		return "admin/home";
	}
	
}
