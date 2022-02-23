package com.portafolio.crud.education;

import java.io.IOException;
import java.util.Set;
import java.util.List;


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

import com.portafolio.crud.cloudinary.CloudinaryService;
import com.portafolio.crud.cloudinary.Image;
import com.portafolio.crud.cloudinary.ImageService;
import com.portafolio.security.entity.User;
import com.portafolio.security.service.UserService;
import com.portafolio.util.Message;

@RestController
@RequestMapping("/education")
@CrossOrigin(origins = "*")
public class EducationController {
	
	@Autowired
    EducationService educationService;
	@Autowired
	UserService userService;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImageService imageService;
	
	

    /*
     * Get with username
     * */
    @GetMapping("/get/{username}")
    public ResponseEntity<List<Education>> getList(@PathVariable("username") String username){
    	
    	Set<Education> list = educationService.findByUserId(
    				userService.getByUsername(username).get().getId());
    	return new ResponseEntity(list, HttpStatus.OK);
    	
    }


    /*
	 * Create work experience with the username
	 * */   
    @PostMapping("/create/{username}")
    public ResponseEntity<?> create(@PathVariable("username") String username, @RequestBody Education educationDto){
    	if(StringUtils.isEmpty(educationDto.getDegree()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
    	
    	User user = userService.getByUsername(username).get();
    	Education education = new Education();
    	
    	education.setInstitution(educationDto.getInstitution());
    	education.setDegree(educationDto.getDegree());
    	education.setDate(educationDto.getDate());
    	education.setPeriod(educationDto.getPeriod());
    	education.setUser(user);
    	
    	Image image = new Image();
    	Image imageDefault = imageService.getOne((long) 160).get();
		image.setImageId(imageDefault.getImageId());
		image.setImageUrl(imageDefault.getImageUrl());
		image.setName(imageDefault.getName());
		image = imageService.save(image);
		education.setImage(image);
    	
	    educationService.save(education);
	    
	    return new ResponseEntity(education, HttpStatus.OK);
	   
    }
    
    /*
     * Update only the text
     * */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Education educationDto) throws IOException{
    	
        Education education = educationService.findById(educationDto.getId()).get();
    	
        education.setInstitution(educationDto.getInstitution());
    	education.setDegree(educationDto.getDegree());
    	education.setDate(educationDto.getDate());
    	education.setPeriod(educationDto.getPeriod());
    	
    	// delete from cloudinary before update the image in database
    	cloudinaryService.delete(educationService.findById(educationDto.getId()).get().getImage().getImageId());
    	education.setImage(educationDto.getImage());
    	
    	educationService.save(education);
        	
        return new ResponseEntity(education, HttpStatus.OK);
    }
  
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws IOException{
    	
    	cloudinaryService.delete(educationService.findById(id).get().getImage().getImageId());
        educationService.delete(id);
        return new ResponseEntity(new Message("education deleted"), HttpStatus.OK);
    }

}
