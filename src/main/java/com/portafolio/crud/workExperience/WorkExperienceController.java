package com.portafolio.crud.workExperience;

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
@RequestMapping("/workExperience")
@CrossOrigin(origins = "*")
public class WorkExperienceController {

	@Autowired
    WorkExperienceService workExperienceService;
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
    public ResponseEntity<List<WorkExperience>> getList(@PathVariable("username") String username){
    	
    	Set<WorkExperience> list = workExperienceService.findByUserId(
    				userService.getByUsername(username).get().getId());
    	return new ResponseEntity(list, HttpStatus.OK);
    	
    }


    /*
	 * Create work experience with the username
	 * */   
    @PostMapping("/create/{username}")
    public ResponseEntity<?> create(@PathVariable("username") String username, @RequestBody WorkExperience workExperienceDto) throws IOException{
    	if(StringUtils.isEmpty(workExperienceDto.getDegree()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
    	
    	User user = userService.getByUsername(username).get();
    	WorkExperience workExperience = new WorkExperience();
    	long idImage = 0;
    	
    	// if is an update
    	if (workExperienceDto.getId() != null) {
    		workExperience = workExperienceService.findById(workExperienceDto.getId()).get();
    		if (workExperience.getImage() != null && workExperienceDto.getImage() != null && workExperience.getImage().getId() != workExperienceDto.getImage().getId()) {
        		cloudinaryService.delete(workExperience.getImage().getImageId());
    			idImage = workExperience.getImage().getId();
        	}
    	}
    	
    	workExperience.setDegree(workExperienceDto.getDegree());
    	workExperience.setStart(workExperienceDto.getStart());
    	workExperience.setfinished(workExperienceDto.getfinished());
    	workExperience.setInProgress(workExperienceDto.getInProgress());
    	workExperience.setDescription(workExperienceDto.getDescription());
    	workExperience.setImage(workExperienceDto.getImage());
    	workExperience.setUser(user);
		
	    workExperienceService.save(workExperience);
	    
	    if (idImage > 0) {
	    	imageService.delete(idImage);
	    }
	    
	    return new ResponseEntity(workExperience, HttpStatus.OK);
	   
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws IOException{
    	long idImage = 0;
    	// if theres is not information
    	if(!workExperienceService.existsById(id)) {
    		return new ResponseEntity(new Message("The work experience not exists"), HttpStatus.BAD_REQUEST);
        }
    	if (workExperienceService.findById(id).get().getImage() != null) {
    		idImage = workExperienceService.findById(id).get().getImage().getId();
    	}
        
    	workExperienceService.delete(id);
        
        if (idImage > 0) {
        	cloudinaryService.delete(imageService.getOne(idImage).get().getImageId());
        	imageService.delete(idImage);
        }
        return new ResponseEntity(new Message("workExperience deleted"), HttpStatus.OK);
    }
    
}
