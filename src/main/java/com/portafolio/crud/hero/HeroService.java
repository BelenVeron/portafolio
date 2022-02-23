package com.portafolio.crud.hero;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeroService {
	
	@Autowired
	HeroRepository repository;

    public void  save(Hero hero){
        repository.save(hero);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	public Optional<Hero> findById(Long id) {
		return repository.findById(id);
	}

	public Optional<Hero> findByUserId(Long id) {
		return repository.findByUserId(id);
	}


}
