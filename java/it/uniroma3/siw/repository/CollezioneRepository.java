package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Collezione;
import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.model.Opera;

public interface CollezioneRepository extends CrudRepository<Collezione, Long> {

	List<Collezione> findCollezioneByCuratore(Curatore curatore);
	

}
