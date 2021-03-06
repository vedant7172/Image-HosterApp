package com.upgrad.technical.api.controller;


import com.upgrad.technical.service.business.AdminService;
import com.upgrad.technical.service.entity.ImageEntity;
import com.upgrad.technical.service.exception.ImageNotFoundException;
import com.upgrad.technical.service.exception.UnauthorizedException;
import com.upgrad.technical.service.exception.UserNotSignedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @RequestMapping(method = RequestMethod.GET, path = "/images/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ImageEntity> getImage(@PathVariable("id") final String imageUuid, @RequestHeader("authorization") final String authorization) throws ImageNotFoundException, UnauthorizedException, UserNotSignedInException {

        final ImageEntity imageEntity = adminService.getImage(imageUuid, authorization);

        //ImageDetailsResponse imageDetailsResponse = new ImageDetailsResponse().image(imageEntity.getImage()).id((int) imageEntity.getId()).name(imageEntity.getName()).description(imageEntity.getDescription()).status(imageEntity.getStatus());
        return new ResponseEntity<>(imageEntity, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/images/update/{image_id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ImageEntity updateImage(final ImageEntity updateImageRequest, @PathVariable("image_id") final long image_id, @RequestHeader("authorization") final String authorization) throws ImageNotFoundException, UnauthorizedException, UserNotSignedInException {
        ImageEntity imageEntity = new ImageEntity();

        imageEntity.setImage(updateImageRequest.getImage());
        imageEntity.setId(image_id);
        imageEntity.setName(updateImageRequest.getName());
        imageEntity.setStatus(updateImageRequest.getStatus());
        imageEntity.setDescription(updateImageRequest.getDescription());


        ImageEntity updatedimageEntity = adminService.updateImage(imageEntity, authorization);
        // UpdateImageResponse updateImageResponse = new UpdateImageResponse().id((int) updatedimageEntity.getId()).status(updatedimageEntity.getStatus());

        return updatedimageEntity;
    }

}
