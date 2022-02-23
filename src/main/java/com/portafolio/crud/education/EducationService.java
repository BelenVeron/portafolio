package com.portafolio.crud.education;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EducationService {

	@Autowired
	EducationRepository repository;
	
	public Set<Education> list(){
        return (Set<Education>) repository.findAll();
    }

    public void  save(Education education){
        repository.save(education);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	public Optional<Education> findById(Long id) {
		return repository.findById(id);
	}

	public Set<Education> findByUserId(Long id) {
		return repository.findByUserId(id);
	}

}
