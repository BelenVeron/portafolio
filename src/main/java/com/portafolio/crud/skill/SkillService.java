package com.portafolio.crud.skill;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

	@Autowired
	SkillRepository repository;
	
	public Set<Skill> list(){
        return (Set<Skill>) repository.findAll();
    }

    public void  save(Skill skill){
        repository.save(skill);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	public Optional<Skill> findById(Long id) {
		return repository.findById(id);
	}

	public Set<Skill> findByUserId(Long id) {
		return repository.findByUserId(id);
	}
}
