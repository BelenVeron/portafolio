package com.portafolio.crud.cloudinary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.portafolio.util.Message;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cloudinary")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImageService imageService;

    @GetMapping("/list")
    public ResponseEntity<List<Image>> list(){
        List<Image> list = imageService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<Image> get(@PathVariable("id") Long id){
    	Optional<Image> image = imageService.getOne(id);
        return new ResponseEntity(image, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Image> upload(@RequestParam MultipartFile multipartFile)throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if(bi == null){
            return new ResponseEntity(new Message("Imagen no válida"), HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        Image image =
                new Image((String)result.get("original_filename"),
                        (String)result.get("url"),
                        (String)result.get("public_id"));
        imageService.save(image);
        return new ResponseEntity(image, HttpStatus.OK);
    }
    
    @PostMapping("/upload-url")
    public ResponseEntity<Image> uploadRemoteUrl(@RequestParam String url)throws IOException {
    	System.out.println(url);
        Map result = cloudinaryService.uploadRemoteUrl(url);
        Image image =
                new Image((String)result.get("original_filename"),
                        (String)result.get("url"),
                        (String)result.get("public_id"));
        return new ResponseEntity(imageService.save(image), HttpStatus.OK);
    }
    
    @PostMapping("/upload-host")
    public ResponseEntity<Image> uploadHost(@RequestParam MultipartFile multipartFile)throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if(bi == null){
            return new ResponseEntity(new Message("Imagen no válida"), HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        ImageDto image =
                new ImageDto((String)result.get("original_filename"),
                        (String)result.get("url"),
                        (String)result.get("public_id"));
        return new ResponseEntity(image, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)throws IOException {
        if(!imageService.exists(id))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        Image image = imageService.getOne(id).get();
        Map result = cloudinaryService.delete(image.getImageId());
        imageService.delete(id);
        return new ResponseEntity(new Message("Imagne eliminada"), HttpStatus.OK);
    }
}