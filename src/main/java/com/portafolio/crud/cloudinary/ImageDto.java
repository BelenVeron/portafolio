package com.portafolio.crud.cloudinary;

public class ImageDto {
	
	private String name;
    private String imageUrl;
    private String imageId;
    
	public ImageDto() {
	}

	public ImageDto(String name, String imageUrl, String imageId) {
		super();
		this.name = name;
		this.imageUrl = imageUrl;
		this.imageId = imageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
    

}
