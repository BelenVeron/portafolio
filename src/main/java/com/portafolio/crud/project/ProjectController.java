package com.portafolio.crud.project;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
import com.portafolio.crud.cloudinary.Image;
import com.portafolio.crud.cloudinary.ImageService;
import com.portafolio.crud.education.Education;
import com.portafolio.security.entity.User;
import com.portafolio.security.service.UserService;
import com.portafolio.util.Message;


@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "*")
public class ProjectController {
	
	@Autowired
    ProjectService projectService;
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
    public ResponseEntity<List<Project>> getList(@PathVariable("username") String username){
    	
    	Set<Project> list = projectService.findByUserId(
    				userService.getByUsername(username).get().getId());
    	
    	return new ResponseEntity(list, HttpStatus.OK);
    	
    }


    /*
	 * Create or update personal information with the user id
	 * */ 
    @PostMapping("/create/{username}")
    public ResponseEntity<?> create(@PathVariable("username") String username, @RequestBody Project projectDto){
    	if(StringUtils.isEmpty(projectDto.getName()))
            return new ResponseEntity(new Message("the name is necessary"), HttpStatus.BAD_REQUEST);
    	
    	User user = userService.getByUsername(username).get();
    	Project project = new Project();
    	
    	project.setName(projectDto.getName());
    	project.setDate(projectDto.getDate());
    	project.setDescription(projectDto.getDescription());
    	project.setLink(projectDto.getLink());
    	project.setUser(user);
    	
    	Image image = new Image();
    	Image imageDefault = imageService.getOne((long) 160).get();
		image.setImageId(imageDefault.getImageId());
		image.setImageUrl(imageDefault.getImageUrl());
		image.setName(imageDefault.getName());
		image = imageService.save(image);
		project.setImage(image);
    	
	    projectService.save(project);
	    
	    return new ResponseEntity(project, HttpStatus.OK);
	   
    }

    /*
     * Update only the image
     * */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Project projectDto) throws IOException{
        
    	Project project = projectService.findById(projectDto.getId()).get();
    	
        project.setName(projectDto.getName());
    	project.setDescription(projectDto.getDescription());
    	project.setDate(projectDto.getDate());
    	project.setLink(projectDto.getLink());
    	
    	// delete from cloudinary before update the image in database
    	cloudinaryService.delete(projectService.findById(projectDto.getId()).get().getImage().getImageId());
    	project.setImage(projectDto.getImage());
    	
    	projectService.save(project);
        	
        return new ResponseEntity(project, HttpStatus.OK);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws IOException{
    	
    	// if theres is not information
    	if(!projectService.existsById(id)) {
    		return new ResponseEntity(new Message("The personal information not exists"), HttpStatus.BAD_REQUEST);
        }
         	
    	cloudinaryService.delete(projectService.findById(id).get().getImage().getImageId());
        projectService.delete(id);
  
        return new ResponseEntity(new Message("project deleted"), HttpStatus.OK);
    }
    

}
