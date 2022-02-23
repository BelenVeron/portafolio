package com.portafolio.crud.personalInformation;

import java.io.IOException;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portafolio.crud.cloudinary.CloudinaryService;
import com.portafolio.crud.cloudinary.Image;
import com.portafolio.crud.cloudinary.ImageService;
import com.portafolio.security.service.UserService;
import com.portafolio.util.Message;


@RestController
@RequestMapping("/personalInformation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonalInformationController {

	@Autowired
    PersonalInformationService personalInformationService;
	@Autowired
	UserService userService;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImageService imageService;
	

    /*
     * Get with user id
     * */
    @GetMapping("/get/{username}")
    public ResponseEntity<PersonalInformation> getOne(@PathVariable("username") String username){
    
    	if (personalInformationService.findByUserId(userService.getByUsername(username).get().getId()).isEmpty()) {
    		return new ResponseEntity(new Message("there is not information"), HttpStatus.BAD_REQUEST);
    	}else {
    		Optional<PersonalInformation> personalInformation = 
    				personalInformationService.findByUserId(userService.getByUsername(username).get().getId());
    		return new ResponseEntity(personalInformation, HttpStatus.OK);
    	}
    	
    }


    /*
	 * Create or update personal information with the user id
	 * */ 
    @PostMapping("/create/{username}")
    public ResponseEntity<?> create(@PathVariable("username") String username, @RequestBody PersonalInformationDto personalInformationDto) throws IOException{
    	if(StringUtils.isEmpty(personalInformationDto.getName()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
    	
    	Image image = new Image();
    	PersonalInformation personalInformation = new PersonalInformation();
    	// only get personal information if exists
    	if (!personalInformationService.findByUserId(userService.getByUsername(username).get().getId()).isEmpty()) {
    		personalInformation = personalInformationService.findByUserId(userService.getByUsername(username).get().getId()).get();
        	cloudinaryService.delete(personalInformation.getImage().getImageId());
    		image = imageService.save(personalInformationDto.getImage());
    	}else {
    		// without image, to save the first information
    		Image imageDefault = imageService.getOne((long) 72).get();
    		image.setImageId(imageDefault.getImageId());
    		image.setImageUrl(imageDefault.getImageUrl());
    		image.setName(imageDefault.getName());
    		image = imageService.save(image);
    	}
    	
    	personalInformation.setName(personalInformationDto.getName());
    	personalInformation.setDegree(personalInformationDto.getDegree());
    	personalInformation.setSummary(personalInformationDto.getSummary());
    	personalInformation.setImage(image);
    	personalInformation.setUser(userService.getByUsername(username).get());
    	
	    personalInformationService.save(personalInformation);
	    
	    return new ResponseEntity(new Message("personalInformation created"), HttpStatus.OK);
	   
    }

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws IOException{
    	
    	// if theres is not information
    	if(!personalInformationService.existsById(id)) {
    		return new ResponseEntity(new Message("The personal information not exists"), HttpStatus.BAD_REQUEST);
        }
        cloudinaryService.delete(personalInformationService.findById(id).get().getImage().getImageId());
        personalInformationService.delete(id);
  
        return new ResponseEntity(new Message("personalInformation deleted"), HttpStatus.OK);
    }
    
}
