package com.app.codingTogether.service.image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.app.codingTogether.config.CloudinaryConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Component
public class ImageUploader {

	@Autowired
	CloudinaryConfig cloudinaryConfig;
	
	  public String uploadImages(MultipartFile image) {
	            try {
	            	if(image==null) System.out.println("hola");
	            	System.out.println(image.isEmpty());
	                Map uploadResult = cloudinaryConfig.cloudinary().uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
	                return (String) uploadResult.get("url");
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        
	        return null;
	    }
}
