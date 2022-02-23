package com.portafolio.crud.project;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository repository;
	
	public Set<Project> list(){
        return (Set<Project>) repository.findAll();
    }

    public void  save(Project project){
        repository.save(project);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	public Optional<Project> findById(Long id) {
		return repository.findById(id);
	}

	public Set<Project> findByUserId(Long id) {
		return repository.findByUserId(id);
	}


}
