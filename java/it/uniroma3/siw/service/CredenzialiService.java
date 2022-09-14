package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;

@Service
public class CredenzialiService {

	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected CredenzialiRepository credenzialiRepository;

	@Transactional
	public Credenziali getCredenziali(String username) {
		Optional<Credenziali> result = credenzialiRepository.findAllByUsername(username);
		return result.orElse(null);
	}

	@Transactional
	public void saveCredenziali(Credenziali credenziali) {
		credenziali.setRuolo(Credenziali.DEFAULT_ROLE);
		credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
		this.credenzialiRepository.save(credenziali);
	}
}
