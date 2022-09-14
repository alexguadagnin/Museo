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

import it.uniroma3.siw.model.Collezione;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.service.CollezioneService;
import it.uniroma3.siw.service.OperaService;

@Controller
public class CollezioneController {

	@Autowired
	private CollezioneService collezioneService;

	@Autowired
	private OperaService operaService;

	@RequestMapping(value = "/admin/modificaCollezione/{id}", method = RequestMethod.GET) 
	public String toModificaCollezione (@PathVariable("id") Long id, Model model) {
		model.addAttribute("collezione", collezioneService.getById(id));
		return "admin/modificaCollezione";
	}

	@RequestMapping(value = "/admin/modificaCollezione/{id}", method = RequestMethod.POST) 
	public String modificaCollezione (@PathVariable("id") Long id, @ModelAttribute("collezione") Collezione collezione, Model model) {
		Collezione collezioneDaModificare = collezioneService.getById(id);
		collezioneDaModificare.setDescrizione(collezione.getDescrizione());
		collezioneService.saveCollezione(collezioneDaModificare);

		model.addAttribute("collezioni", collezioneService.getAll());
		model.addAttribute("opere", operaService.getAll());
		model.addAttribute("statoModificaSuccesso", "successo");
		return "admin/home";
	}

	@RequestMapping(value = "/admin/eliminaCollezione/{id}", method = RequestMethod.GET) 
	public String toEliminaCollezione (@PathVariable("id") Long id, Model model) {
		List<Opera> opere = collezioneService.getById(id).getOpere();
		for(Opera opera : opere) {
			opera.setCollezione(null);
			operaService.save(opera);	
		}
		collezioneService.deleteById(id);
		
		model.addAttribute("collezioni", collezioneService.getAll());
		model.addAttribute("opere", operaService.getAll());
		model.addAttribute("statoCancellazioneSuccesso", "successo");
		return "admin/home";
	}

}
