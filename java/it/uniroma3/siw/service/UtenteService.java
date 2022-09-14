package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService {

	@Autowired
	protected UtenteRepository utenteRepository;

	@Transactional
	public void saveUtente(Utente utente) {
		utenteRepository.save(utente);
		
	}
	
}
