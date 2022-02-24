package com.portafolio.crud.hero;

import java.io.IOException;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/hero")
@CrossOrigin(origins = "*")
public class HeroController {
	
	@Autowired
    HeroService heroService;
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
    public ResponseEntity<Hero> getOne(@PathVariable("username") String username){
    	Optional<Hero> hero = heroService.findByUserId(userService.getByUsername(username).get().getId());
    	return new ResponseEntity(hero, HttpStatus.OK);
    }


    /*
     * Because the database only have an image, create and update is in the same url
     * */
    @PostMapping("/create/{username}")
    public ResponseEntity<?> update(@PathVariable("username") String username, @RequestBody HeroDto heroDto) throws IOException{
    	if (heroDto.getImage() == null) {
    		return new ResponseEntity(new Message("Must be containt an image"), HttpStatus.BAD_REQUEST);
    	}else {
        	Hero hero = new Hero();
        	// only get personal information if exists
        	System.out.println(heroDto.getImage());
        	if (heroService.findByUserId(userService.getByUsername(username).get().getId()).isPresent()){
        		hero = heroService.findByUserId(userService.getByUsername(username).get().getId()).get();
            	cloudinaryService.delete(hero.getImage().getImageId());	
        	}
        	
        	hero.setImage(heroDto.getImage());
        	hero.setUser(userService.getByUsername(username).get());
        	
    	    heroService.save(hero);
    	    
    	    return new ResponseEntity(hero, HttpStatus.OK);
    	}
    	
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws IOException{
    	
    	// if theres is not information
    	if(!heroService.existsById(id)) {
    		return new ResponseEntity(new Message("The hero not exists"), HttpStatus.BAD_REQUEST);
        }
    	cloudinaryService.delete(heroService.findById(id).get().getImage().getImageId());
        heroService.delete(id);
  
        return new ResponseEntity(new Message("hero deleted"), HttpStatus.OK);
    }

}
