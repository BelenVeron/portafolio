package com.portafolio.crud.education;

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
    	
	    educationService.save(education);
	    
	    return new ResponseEntity(new Message("education created"), HttpStatus.OK);
	   
    }
    
    /*
     * Update only the text
     * */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody EducationDto educationDto) throws IOException{
    	
        Education education = educationService.findById(id).get();
    	
        education.setInstitution(educationDto.getInstitution());
    	education.setDegree(educationDto.getDegree());
    	education.setDate(educationDto.getDate());
    	education.setPeriod(educationDto.getPeriod());
    	
    	educationService.save(education);
        	
        return new ResponseEntity(new Message("education updated"), HttpStatus.OK);
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
    	Education education = educationService.findById(id).get();
        education.setImage(image);
        educationService.save(education);
        	
        return new ResponseEntity(new Message("education updated"), HttpStatus.OK);
    }
  
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
    	
        educationService.delete(id);
        return new ResponseEntity(new Message("education deleted"), HttpStatus.OK);
    }

}
