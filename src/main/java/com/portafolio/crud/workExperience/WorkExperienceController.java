package com.portafolio.crud.workExperience;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.Column;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portafolio.crud.cloudinary.CloudinaryService;
import com.portafolio.crud.cloudinary.Image;
import com.portafolio.crud.workExperience.WorkExperience;
import com.portafolio.crud.workExperience.WorkExperienceDto;
import com.portafolio.crud.workExperience.WorkExperienceService;
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
    	workExperience.setImage(workExperienceDto.getImage());
    	workExperience.setDescription(workExperienceDto.getDescription());
    	workExperience.setUser(user);
    	
	    workExperienceService.save(workExperience);
	    
	    return new ResponseEntity(new Message("workExperience created"), HttpStatus.OK);
	   
    }
    
    /*
     * Update only the text
     * */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody WorkExperienceDto workExperienceDto) throws IOException{
    	
        WorkExperience workExperience = workExperienceService.findById(id).get();
    	
    	workExperience.setDegree(workExperienceDto.getDegree());
    	workExperience.setStart(workExperienceDto.getStart());
    	workExperience.setEnd(workExperienceDto.getEnd());
    	workExperience.setInProgress(workExperienceDto.getInProgress());
    	workExperience.setDescription(workExperienceDto.getDescription());
    	
    	workExperienceService.save(workExperience);
        	
        return new ResponseEntity(new Message("workExperience updated"), HttpStatus.OK);
    }

    /*
     * Update only the image
     * */
    @PutMapping("/update-image/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestParam MultipartFile multipartFile) throws IOException{
    	
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
        
    	// update user with the image
    	WorkExperience workExperience = workExperienceService.findById(id).get();
        workExperience.setImage(image);
        workExperienceService.save(workExperience);
        	
        return new ResponseEntity(new Message("workExperience updated"), HttpStatus.OK);
    }
  
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
    	
        workExperienceService.delete(id);
        return new ResponseEntity(new Message("workExperience deleted"), HttpStatus.OK);
    }
    
}
