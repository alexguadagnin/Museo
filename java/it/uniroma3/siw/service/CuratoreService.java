package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.repository.CuratoreRepository;

@Service
public class CuratoreService {

	@Autowired
	protected CuratoreRepository curatoreRepository;

	@Transactional
	public List<Curatore> getAll() {
		Iterable<Curatore> iterable = curatoreRepository.findAll();
		List<Curatore> result = new ArrayList<>();
		for(Curatore curatore : iterable) {
			result.add(curatore);
		}

		Collections.sort(result, new Comparator<Curatore>() {
			@Override
			public int compare(Curatore c1, Curatore c2) {
				return c1.getCognome().compareTo(c2.getCognome());
			}
		});

		return result;
	}
	
	@Transactional
	public Curatore getById(Long id) {
		return curatoreRepository.findById(id).get();
	}

}
