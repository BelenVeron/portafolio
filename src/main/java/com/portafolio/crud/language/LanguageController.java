package com.portafolio.crud.language;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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
import com.portafolio.crud.language.Language;
import com.portafolio.crud.language.LanguageDto;
import com.portafolio.crud.language.LanguageService;
import com.portafolio.security.entity.User;
import com.portafolio.security.service.UserService;
import com.portafolio.util.Message;

@RestController
@RequestMapping("/lenguage")
@CrossOrigin(origins = "*")
public class LanguageController {
	
	@Autowired
    LanguageService languageService;
	@Autowired
	UserService userService;
    @Autowired
    CloudinaryService cloudinaryService;
	

    /*
     * Get with username
     * */
    @GetMapping("/get/{username}")
    public ResponseEntity<List<Language>> getList(@PathVariable("username") String username){
    	
    	Set<Language> list = languageService.findByUserId(
    				userService.getByUsername(username).get().getId());
    	return new ResponseEntity(list, HttpStatus.OK);
    	
    }


    /*
	 * Create work experience with the username
	 * */   
    @PostMapping("/create/{username}")
    public ResponseEntity<?> create(@PathVariable("username") String username, @RequestBody Language languageDto){
    	if(StringUtils.isEmpty(languageDto.getName()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
    	
    	User user = userService.getByUsername(username).get();
    	Language language = new Language();
    	
    	language.setName(languageDto.getName());
    	language.setLevel(languageDto.getLevel());
    	language.setUser(user);
    	
	    languageService.save(language);
	    
	    return new ResponseEntity(new Message("language created"), HttpStatus.OK);
	   
    }
    
    /*
     * Update only the text
     * */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody LanguageDto languageDto) throws IOException{
    	
        Language language = languageService.findById(id).get();
    	
        language.setName(languageDto.getName());
    	language.setLevel(languageDto.getLevel());
    	
    	languageService.save(language);
        	
        return new ResponseEntity(new Message("language updated"), HttpStatus.OK);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
    	
        languageService.delete(id);
        return new ResponseEntity(new Message("language deleted"), HttpStatus.OK);
    }


}
