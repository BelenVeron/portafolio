package com.portafolio.crud.personalInformation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

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
	        		personalInformationDto.getDegree(),
	        		personalInformationDto.getSummary(),
	        		id,
	        		new Image());
	        personalInformationService.save(personalInformation);
	        return new ResponseEntity(new Message("personalInformation created"), HttpStatus.OK);
    	}
       
    }

    /*
     * Update only the image
     * */
    @PutMapping("/update-image/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestParam MultipartFile multipartFile) throws IOException{
    	
    	if(!personalInformationService.existsById(id)) {
    		return new ResponseEntity(new Message("The personal information not exists"), HttpStatus.BAD_REQUEST);
        }
    	
        // get image 
    	Image image = new Image();
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if(bi == null){
        	return new ResponseEntity(new Message("Imagen no válida"), HttpStatus.BAD_REQUEST);
        }else {
        	// find, so upload
        	Map result = cloudinaryService.upload(multipartFile);
        	image.setName((String)result.get("original_filename"));
        	image.setImageUrl((String)result.get("url"));
        	image.setImageId((String)result.get("public_id"));
        }
        
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation = personalInformationService.findById(id);
        personalInformation.setImage(image);
        personalInformationService.save(personalInformation);
        return new ResponseEntity(new Message("personalInformation updated"), HttpStatus.OK);
    }
    
    /*
     * Update only the text
     * */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody PersonalInformationDto personalInformationDto) throws IOException{
    	
    	if(!personalInformationService.existsById(personalInformationDto.getId())) {
    		return new ResponseEntity(new Message("The personal information not exists"), HttpStatus.BAD_REQUEST);
        }
    	
        if(StringUtils.isEmpty(personalInformationDto.getName()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
       
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation = personalInformationService.findById(personalInformationDto.getId());
        personalInformation.setName(personalInformationDto.getName());
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
