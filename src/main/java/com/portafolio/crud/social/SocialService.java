package com.portafolio.crud.social;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portafolio.crud.social.Social;

@Service
public class SocialService {
	
	@Autowired
	SocialRepository repository;
	
	public Set<Social> list(){
        return (Set<Social>) repository.findAll();
    }

    public void  save(Social social){
        repository.save(social);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	public Optional<Social> findById(Long id) {
		return repository.findById(id);
	}

	public Set<Social> findByUserId(Long id) {
		return repository.findByUserId(id);
	}

}
