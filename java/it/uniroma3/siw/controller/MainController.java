package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Collezione;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.CollezioneService;
import it.uniroma3.siw.service.CuratoreService;
import it.uniroma3.siw.service.OperaService;

@Controller
public class MainController {

	@Autowired
	private OperaService operaService;

	@Autowired
	private CollezioneService collezioneService;

	@Autowired
	private CuratoreService curatoreService;
	
	@Autowired
	private ArtistaService artistaService;

	@RequestMapping(value = "/visualizzaPerCuratore/{id}", method = RequestMethod.GET)
	public String mostraPerCuratore(@PathVariable("id") Long id, Model model) {
		List<Collezione> collezioni = collezioneService.findCollezioniByCuratore(curatoreService.getById(id));
		List<Opera> result = new ArrayList<>();
		for(Collezione collezione : collezioni) {
			for(Opera opera : collezione.getOpere()) {
				result.add(opera);
			}
		}
		model.addAttribute("opere", result);
		return "galleria";
	}
	
	@RequestMapping(value = "/visualizzaPerAutore/{id}", method = RequestMethod.GET)
	public String mostraPerAutore(@PathVariable("id") Long id, Model model) {
		List<Opera> result = artistaService.getById(id).getOpere();
		
		model.addAttribute("opere", result);
		return "galleria";
	}
	
	@RequestMapping(value = "/visualizzaPerAnno/{anno}", method = RequestMethod.GET)
	public String mostraPerAutore(@PathVariable("anno") Integer anno, Model model) {
		List<Opera> result = operaService.getByAnno(anno);
		
		model.addAttribute("opere", result);
		return "galleria";
	}

}
