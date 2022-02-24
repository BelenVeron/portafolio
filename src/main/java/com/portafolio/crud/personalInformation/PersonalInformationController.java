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
    	Optional<PersonalInformation> personalInformation = personalInformationService.findByUserId(userService.getByUsername(username).get().getId());
    	return new ResponseEntity(personalInformation, HttpStatus.OK);
    }


    /*
	 * Create or update personal information with the username.
	 * Important: if the Dto's Image is null, it's gonna be remove
	 * */ 
    @PostMapping("/create/{username}")
    public ResponseEntity<?> create(@PathVariable("username") String username, @RequestBody PersonalInformationDto personalInformationDto) throws IOException{
    	if(StringUtils.isEmpty(personalInformationDto.getName()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
    	
    	PersonalInformation personalInformation = new PersonalInformation();
    	long idImage = 0;
    	// only get personal information if exists
    	if (personalInformationService.findByUserId(userService.getByUsername(username).get().getId()).isPresent()) {
    		personalInformation = personalInformationService.findByUserId(userService.getByUsername(username).get().getId()).get();
    		if (personalInformationDto.getImage() != null && personalInformation.getImage() != null) {
    			cloudinaryService.delete(personalInformation.getImage().getImageId());
    			idImage = personalInformation.getImage().getId();
    		}
    	}
    	
    	personalInformation.setName(personalInformationDto.getName());
    	personalInformation.setDegree(personalInformationDto.getDegree());
    	personalInformation.setSummary(personalInformationDto.getSummary());
    	personalInformation.setImage(personalInformationDto.getImage());
    	personalInformation.setUser(userService.getByUsername(username).get());
    	
	    personalInformationService.save(personalInformation);
	    
	    // need the id and delete after the personal information
	    // is saved, because the referential image with personal information
	    if (idImage > 0) {
	    	imageService.delete(idImage);
	    }
	    
	    return new ResponseEntity(personalInformation, HttpStatus.OK);  
    }

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws IOException{
    	long idImage = 0;
    	// if theres is not information
    	if(!personalInformationService.existsById(id)) {
    		return new ResponseEntity(new Message("The personal information not exists"), HttpStatus.BAD_REQUEST);
        }
    	if (personalInformationService.findById(id).get().getImage() != null) {
    		idImage = personalInformationService.findById(id).get().getImage().getId();
    	}
        
        personalInformationService.delete(id);
        
        if (idImage > 0) {
        	cloudinaryService.delete(imageService.getOne(idImage).get().getImageId());
        	imageService.delete(idImage);
        }
  
        return new ResponseEntity(new Message("personalInformation deleted"), HttpStatus.OK);
    }
    
}
