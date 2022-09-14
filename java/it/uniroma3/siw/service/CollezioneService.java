package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Collezione;
import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.repository.CollezioneRepository;

@Service
public class CollezioneService {

	@Autowired
	protected CollezioneRepository collezioneRepository;

	@Transactional
	public List<Collezione> findCollezioniByCuratore(Curatore curatore) {
		return collezioneRepository.findCollezioneByCuratore(curatore); 
	}

	@Transactional
	public List<Collezione> getAll() {
		Iterable<Collezione> iterable = collezioneRepository.findAll();
		List<Collezione> result = new ArrayList<>();
		for(Collezione collezione : iterable) {
			result.add(collezione);
		}
		
		Collections.sort(result, new Comparator<Collezione>() {
			@Override
			public int compare(Collezione c1, Collezione c2) {
				return c1.getId().compareTo(c2.getId());
			}
		});
		
		return result;
	}

	@Transactional
	public Collezione getById(Long id) {
		return collezioneRepository.findById(id).get();
	}

	@Transactional
	public void saveCollezione(Collezione collezioneDaModificare) {
		collezioneRepository.save(collezioneDaModificare);
	}

	@Transactional
	public void deleteById(Long id) {
		collezioneRepository.deleteById(id);
	}

}
