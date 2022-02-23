package com.portafolio.crud.skill;

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
import com.portafolio.security.entity.User;
import com.portafolio.security.service.UserService;
import com.portafolio.util.Message;

@RestController
@RequestMapping("/skill")
@CrossOrigin(origins = "*")
public class SkillController {
	
	@Autowired
    SkillService skillService;
	@Autowired
	UserService userService;
    @Autowired
    CloudinaryService cloudinaryService;
	

    /*
     * Get with username
     * */
    @GetMapping("/get/{username}")
    public ResponseEntity<List<Skill>> getList(@PathVariable("username") String username){
    	
    	Set<Skill> list = skillService.findByUserId(
    				userService.getByUsername(username).get().getId());
    	return new ResponseEntity(list, HttpStatus.OK);
    	
    }


    /*
	 * Create work experience with the username
	 * */   
    @PostMapping("/create/{username}")
    public ResponseEntity<?> create(@PathVariable("username") String username, @RequestBody Skill skillDto){
    	if(StringUtils.isEmpty(skillDto.getName()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
    	
    	User user = userService.getByUsername(username).get();
    	Skill skill = new Skill();
    	
    	skill.setName(skillDto.getName());
    	skill.setPercent(skillDto.getPercent());
    	skill.setUser(user);
    	
	    skillService.save(skill);
	    
	    return new ResponseEntity(skill, HttpStatus.OK);
	   
    }
    
    /*
     * Update only the text
     * */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Skill skillDto) throws IOException{
    	
    	Skill skill = skillService.findById(skillDto.getId()).get();
    	skill.setName(skillDto.getName());
    	skill.setPercent(skillDto.getPercent());
    	skillService.save(skill);
        return new ResponseEntity(skill, HttpStatus.OK);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
    	
        skillService.delete(id);
        return new ResponseEntity(new Message("skill deleted"), HttpStatus.OK);
    }

}
