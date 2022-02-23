package com.portafolio.crud.social;

import java.io.IOException;
import java.util.Iterator;
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
import com.portafolio.crud.social.Social;
import com.portafolio.crud.social.SocialDto;
import com.portafolio.crud.social.SocialService;
import com.portafolio.security.entity.User;
import com.portafolio.security.service.UserService;
import com.portafolio.util.Message;

@RestController
@RequestMapping("/social")
@CrossOrigin(origins = "*")
public class SocialController {
	
	@Autowired
    SocialService socialService;
	@Autowired
	UserService userService;
    @Autowired
    CloudinaryService cloudinaryService;
	

    /*
     * Get with username
     * */
    @GetMapping("/get/{username}")
    public ResponseEntity<List<Social>> getList(@PathVariable("username") String username){
    	
    	Set<Social> list = socialService.findByUserId(
    				userService.getByUsername(username).get().getId());
    	return new ResponseEntity(list, HttpStatus.OK);
    	
    }


    /*
	 * Create work experience with the username
	 * */   
    @PostMapping("/create/{username}")
    public ResponseEntity<?> create(@PathVariable("username") String username, @RequestBody SocialDto socialDto){
    	if(StringUtils.isEmpty(socialDto.getSocial()))
            return new ResponseEntity(new Message("Name does not exist or is incorrect"), HttpStatus.BAD_REQUEST);
    	
    	Set<Social> list = socialService.findByUserId(userService.getByUsername(username).get().getId());
    	for(Social social : list) {
    		if (social.getSocial().equals(socialDto.getSocial()))
    			return new ResponseEntity(new Message("Name already exists, change it"), HttpStatus.BAD_REQUEST);
    	}
    	
    	
    	
    	User user = userService.getByUsername(username).get();
    	Social social = new Social();
    	
    	social.setSocial(socialDto.getSocial());
    	social.setLink(socialDto.getLink());
    	social.setUser(user);
    	
	    socialService.save(social);
	    
	    return new ResponseEntity(social, HttpStatus.OK);
	   
    }
    
    /*
     * Update only the text
     * */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Social socialDto) throws IOException{
    	
        Social social = socialService.findById(socialDto.getId()).get();
    	
        social.setSocial(socialDto.getSocial());
    	social.setLink(socialDto.getLink());
    	
    	socialService.save(social);
        	
        return new ResponseEntity(social, HttpStatus.OK);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
    	
        socialService.delete(id);
        return new ResponseEntity(new Message("social deleted"), HttpStatus.OK);
    }
}
