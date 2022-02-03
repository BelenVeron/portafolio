package com.portafolio.security.service;

import com.portafolio.security.entity.User;
import com.portafolio.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> getById(Long id){
        return userRepository.findById(id);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void save(User usuario){
        userRepository.save(usuario);
    }

	public boolean existsById(Long id) {
		return userRepository.existsById(id);
	}

}
