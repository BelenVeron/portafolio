package com.portafolio.crud.workExperience;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkExperienceService {

	@Autowired
	WorkExperienceRepository repository;
	
	public Set<WorkExperience> list(){
        return (Set<WorkExperience>) repository.findAll();
    }

    public void  save(WorkExperience workExperience){
        repository.save(workExperience);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	public Optional<WorkExperience> findById(Long id) {
		return repository.findById(id);
	}

	public Set<WorkExperience> findByUserId(Long id) {
		return repository.findByUserId(id);
	}

}
