package com.portafolio.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.portafolio.crud.cloudinary.Image;
import com.portafolio.crud.cloudinary.ImageService;

public class ImageUtil {
	
	@Autowired
    ImageService imageService;
	
	// without image, to save the first information
	public Image createNewImageDB(int id) {
		Image imageDefault = imageService.getOne((long) id).get();
		Image image = new Image();
		image.setImageId(imageDefault.getImageId());
		image.setImageUrl(imageDefault.getImageUrl());
		image.setName(imageDefault.getName());
		return imageService.save(image);
	}

}
