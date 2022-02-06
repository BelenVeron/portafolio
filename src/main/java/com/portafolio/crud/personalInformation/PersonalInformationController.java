package com.portafolio.crud.personalInformation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portafolio.crud.cloudinary.CloudinaryService;
import com.portafolio.crud.cloudinary.Image;
import com.portafolio.security.entity.User;
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
    @Autowired
    CloudinaryService cloudinaryService;
	

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
    public ResponseEntity<?> create(@PathVariable("username") String username, @RequestBody PersonalInformationDto personalInformationDto){
    	if(StringUtils.isEmpty(personalInformationDto.getName()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
    	
    	PersonalInformation personalInformation = new PersonalInformation();
    	// only get personal information if exists
    	if (!personalInformationService.findByUserId(userService.getByUsername(username).get().getId()).isEmpty())
    		personalInformation = personalInformationService.findByUserId(userService.getByUsername(username).get().getId()).get();
    	
    	personalInformation.setName(personalInformationDto.getName());
    	personalInformation.setDegree(personalInformationDto.getDegree());
    	personalInformation.setSummary(personalInformationDto.getSummary());
    	personalInformation.setUser(userService.getByUsername(username).get());
    	
	    personalInformationService.save(personalInformation);
	    
	    return new ResponseEntity(new Message("personalInformation created"), HttpStatus.OK);
	   
    }

    /*
     * Update only the image
     * */
    @PutMapping("/update-image/{username}")
    public ResponseEntity<?> update(@PathVariable("username") String username, @RequestParam MultipartFile multipartFile) throws IOException{
    	
        // get image 
    	Image image = new Image();
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if(bi == null)
        	return new ResponseEntity(new Message("Imagen no válida"), HttpStatus.BAD_REQUEST);
        	
        // save image in cloudinary and set the properties
    	Map result = cloudinaryService.upload(multipartFile);
    	image.setName((String)result.get("original_filename"));
    	image.setImageUrl((String)result.get("url"));
    	image.setImageId((String)result.get("public_id"));
        
    	// update the image
    	PersonalInformation personalInformation = 
    			personalInformationService.findByUserId(userService.getByUsername(username).get().getId()).get();
        personalInformation.setImage(image);
        personalInformationService.save(personalInformation);
        	
        return new ResponseEntity(new Message("personalInformation updated"), HttpStatus.OK);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
    	
    	// if theres is not information
    	if(!personalInformationService.existsById(id)) {
    		return new ResponseEntity(new Message("The personal information not exists"), HttpStatus.BAD_REQUEST);
        }
         	
        personalInformationService.delete(id);
  
        return new ResponseEntity(new Message("personalInformation deleted"), HttpStatus.OK);
    }
    
}
