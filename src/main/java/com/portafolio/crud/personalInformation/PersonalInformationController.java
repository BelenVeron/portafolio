package com.portafolio.crud.personalInformation;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

import com.portafolio.security.service.UserService;
import com.portafolio.util.Message;


@RestController
@RequestMapping("/personalInformation")
@CrossOrigin(origins = "*")
public class PersonalInformationController {

	@Autowired
    PersonalInformationService personalInformationService;
	@Autowired
	UserService userService;
	

    @GetMapping("/get/{username}")
    public ResponseEntity<PersonalInformation> getOne(@PathVariable("username") String username){
    	int id = userService.getByUsername(username).get().getId();
    	if(!personalInformationService.existsByUserId(id)) {
    		return new ResponseEntity(new Message("there is not information"), HttpStatus.BAD_REQUEST);
    	}else {
	        PersonalInformation personalInformation = personalInformationService.findByUserId(id);
	        return new ResponseEntity(personalInformation, HttpStatus.OK);
    	}
    }


    /*
	 * Create a new personal information, only when it doesn't exit.
	 * Use the username to find the id of the user, so this must be
	 * in the request body
	 * */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PersonalInformationDto personalInformationDto){
    	
    	int id = userService.getByUsername(personalInformationDto.getUsername()).get().getId();
    	
    	if(personalInformationService.existsByUserId(id)) {
    		return new ResponseEntity(new Message("the personal information exists, update it"), HttpStatus.BAD_REQUEST);
    	}else {
    		if(StringUtils.isEmpty(personalInformationDto.getName()))
	            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
	        
	        PersonalInformation personalInformation = new PersonalInformation(
	        		personalInformationDto.getName(), 
	        		personalInformationDto.getPicture(),
	        		personalInformationDto.getDegree(),
	        		personalInformationDto.getSummary(),
	        		id);
	        personalInformationService.save(personalInformation);
	        return new ResponseEntity(new Message("personalInformation created"), HttpStatus.OK);
    	}
       
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody PersonalInformationDto personalInformationDto){
    	PersonalInformation personalInformation = new PersonalInformation();
    	
    	if(!personalInformationService.existsById(personalInformationDto.getId())) {
    		return new ResponseEntity(new Message("The personal information not exists"), HttpStatus.BAD_REQUEST);
        }
    	
        if(StringUtils.isEmpty(personalInformationDto.getName()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
       
        personalInformation = personalInformationService.findById(personalInformationDto.getId());
        personalInformation.setName(personalInformationDto.getName());
        personalInformation.setPicture(personalInformationDto.getPicture());
        personalInformation.setDegree(personalInformationDto.getDegree());
        personalInformation.setSummary(personalInformationDto.getSummary());
        personalInformationService.save(personalInformation);
        return new ResponseEntity(new Message("personalInformation updated"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody PersonalInformationDto personalInformationDto){
    	
    	if(!personalInformationService.existsById(personalInformationDto.getId())) {
    		return new ResponseEntity(new Message("The personal information not exists"), HttpStatus.BAD_REQUEST);
        }
       
        personalInformationService.delete(personalInformationDto.getId());
        return new ResponseEntity(new Message("personalInformation deleted"), HttpStatus.OK);
    }
    
}
