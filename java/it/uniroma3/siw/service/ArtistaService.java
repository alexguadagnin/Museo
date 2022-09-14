package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.repository.ArtistaRepository;

@Service
public class ArtistaService {

	@Autowired
	protected ArtistaRepository artistaRepository;

	@Transactional
	public List<Artista> getAll() {
		Iterable<Artista> iterable = artistaRepository.findAll();
		List<Artista> result = new ArrayList<>();
		for(Artista artista : iterable) {
			result.add(artista);
		}
		
		Collections.sort(result, new Comparator<Artista>() {
			@Override
			public int compare(Artista a1, Artista a2) {
				//ordina per cognome
				return a1.getCognome().compareTo(a2.getCognome());
			}
		});
		
		return result;
	}

	@Transactional
	public Artista getById(Long id) {
		return artistaRepository.findById(id).get();
	}

}
