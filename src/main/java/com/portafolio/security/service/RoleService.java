package com.portafolio.security.service;

import com.portafolio.security.entity.Role;
import com.portafolio.security.enums.RoleName;
import com.portafolio.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> getByRoleName(RoleName roleName){
        return roleRepository.findByRoleName(roleName);
    }
    
    public Optional<Role> getById(Long id){
        return roleRepository.findById(id);
    }

    public void save(Role role){
        roleRepository.save(role);
    }
    
    public Set<Role> list(){
    	Set<Role> roles = new HashSet<>();
    	for(Role role: roleRepository.findAll()){
    	    roles.add(role);
    	}
        return roles;
    }

	public void delete(Long id) {
		roleRepository.deleteById(id);
	}
}
