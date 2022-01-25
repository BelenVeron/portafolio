package com.portafolio.crud.personalInformation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portafolio.security.service.UserService;

@Service
@Transactional
public class PersonalInformationService {

	@Autowired
	PersonalInformationRepository repository;
	@Autowired
	UserService userService;

    public void  save(PersonalInformation personalInformation){
        repository.save(personalInformation);
    }

    public void delete(int id){
        repository.deleteById(id);
    }

    public boolean existsByUserId(int userId){
        return repository.existsByUserId(userId);
    }

	public boolean existsById(int id) {
		return repository.existsById(id);
	}

	public PersonalInformation findByUserId(int userId) {
		return repository.findByUserId(userId);
	}

	public PersonalInformation findById(int id) {
		return repository.findById(id);
	}

}
