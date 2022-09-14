package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.repository.OperaRepository;

@Service
public class OperaService {

	@Autowired
	protected OperaRepository operaRepository;

	@Transactional
	public Object getAllAnni() {
		Iterable<Opera> iterable = operaRepository.findAll();
		List<Integer> result = new ArrayList<>();
		for(Opera opera : iterable) {
			if(!result.contains(opera.getAnno()))
				result.add(opera.getAnno());
		}

		Collections.sort(result);
		return result;
	}
/*
	public List<Opera> findByArtista(Artista artista) {
		return operaRepository.findByArtista(artista);
	}
*/
	@Transactional
	public List<Opera> getByAnno(Integer anno) {
		return operaRepository.findByAnno(anno);
	}
	
	@Transactional
	public List<Opera> getAll() {
		Iterable<Opera> iterable = operaRepository.findAll();
		List<Opera> result = new ArrayList<>();
		for(Opera opera : iterable) {
			result.add(opera);
		}
		
		Collections.sort(result, new Comparator<Opera>() {
			@Override
			public int compare(Opera o1, Opera o2) {
				return o1.getTitolo().compareTo(o2.getTitolo());
			}
		});
		
		return result;
	}
	
	@Transactional
	public Opera getById(Long id) {
		return operaRepository.findById(id).get();
	}
	
	@Transactional
	public void save(Opera operaDaModificare) {
		operaRepository.save(operaDaModificare);
	}
	
	@Transactional
	public void deleteById(Long id) {
		operaRepository.deleteById(id);
		
	}

}
