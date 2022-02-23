package com.portafolio.crud.workExperience;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.imageio.ImageIO;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> create(@PathVariable("username") String username, @RequestBody WorkExperience workExperienceDto){
    	if(StringUtils.isEmpty(workExperienceDto.getDegree()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
    	
    	User user = userService.getByUsername(username).get();
    	WorkExperience workExperience = new WorkExperience();
    	
    	workExperience.setDegree(workExperienceDto.getDegree());
    	workExperience.setStart(workExperienceDto.getStart());
    	workExperience.setEnd(workExperienceDto.getEnd());
    	workExperience.setInProgress(workExperienceDto.getInProgress());
    	workExperience.setDescription(workExperienceDto.getDescription());
    	workExperience.setUser(user);
    	
    	// set a default image from the database, in this case
    	// with id 160
    	Image image = new Image();
    	Image imageDefault = imageService.getOne((long) 160).get();
		image.setImageId(imageDefault.getImageId());
		image.setImageUrl(imageDefault.getImageUrl());
		image.setName(imageDefault.getName());
		image = imageService.save(image);
		workExperience.setImage(image);
		System.out.println("work: "+image);
		
	    workExperienceService.save(workExperience);
	    
	    return new ResponseEntity(workExperience, HttpStatus.OK);
	   
    }
    
    /*
     * complete update
     * */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody WorkExperienceDto workExperienceDto) throws IOException{
    	
        WorkExperience workExperience = workExperienceService.findById(workExperienceDto.getId()).get();
    	
    	workExperience.setDegree(workExperienceDto.getDegree());
    	workExperience.setStart(workExperienceDto.getStart());
    	workExperience.setEnd(workExperienceDto.getEnd());
    	workExperience.setInProgress(workExperienceDto.getInProgress());
    	workExperience.setDescription(workExperienceDto.getDescription());
    	
    	// delete from cloudinary before update the image in database
    	cloudinaryService.delete(workExperienceService.findById(workExperienceDto.getId()).get().getImage().getImageId());
    	workExperience.setImage(workExperienceDto.getImage());
    	
    	workExperienceService.save(workExperience);
        	
        return new ResponseEntity(workExperience, HttpStatus.OK);
    }

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws IOException{
    	
    	cloudinaryService.delete(workExperienceService.findById(id).get().getImage().getImageId());
        workExperienceService.delete(id);
        return new ResponseEntity(new Message("workExperience deleted"), HttpStatus.OK);
    }
    
}
