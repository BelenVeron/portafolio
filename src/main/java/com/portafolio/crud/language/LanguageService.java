package com.portafolio.crud.language;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

	@Autowired
	LanguageRepository repository;
	
	public Set<Language> list(){
        return (Set<Language>) repository.findAll();
    }

    public void  save(Language language){
        repository.save(language);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	public Optional<Language> findById(Long id) {
		return repository.findById(id);
	}

	public Set<Language> findByUserId(Long id) {
		return repository.findByUserId(id);
	}
}
