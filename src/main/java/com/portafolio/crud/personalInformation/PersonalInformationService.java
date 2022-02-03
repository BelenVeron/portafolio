package com.portafolio.crud.personalInformation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonalInformationService {

	@Autowired
	PersonalInformationRepository repository;

    public void  save(PersonalInformation personalInformation){
        repository.save(personalInformation);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	public Optional<PersonalInformation> findById(Long id) {
		return repository.findById(id);
	}

	public Optional<PersonalInformation> findByUserId(Long id) {
		return repository.findByUserId(id);
	}

}
