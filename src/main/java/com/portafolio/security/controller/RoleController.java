package com.portafolio.security.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portafolio.security.entity.Role;
import com.portafolio.security.entity.User;
import com.portafolio.security.service.RoleService;
import com.portafolio.util.Message;

@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {

	@Autowired
    RoleService roleService;
	
	
    @GetMapping("/get")
    public ResponseEntity<List<Role>> getList(){
    	
    	Set<Role> list = roleService.list();
    	return new ResponseEntity(list, HttpStatus.OK);
    	
    }


    /*
	 * Create work experience with the username
	 * */   
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Role roleDto){
    	if(StringUtils.isEmpty(roleDto.getRoleName()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
  
    	Role role = new Role();
    	
    	role.setRoleName(roleDto.getRoleName());
    	
	    roleService.save(role);
	    
	    return new ResponseEntity(role, HttpStatus.OK);
	   
    }
    
    /*
     * Update only the text
     * */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Role roleDto) throws IOException{
    	
    	Role role = roleService.getById(roleDto.getId()).get();
    	role.setRoleName(roleDto.getRoleName());
    	roleService.save(role);
    	
        return new ResponseEntity(role, HttpStatus.OK);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
    	
        roleService.delete(id);
        return new ResponseEntity(new Message("role deleted"), HttpStatus.OK);
    }

}
