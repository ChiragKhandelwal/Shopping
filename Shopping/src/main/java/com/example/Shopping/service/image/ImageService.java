package com.example.Shopping.service.image;

import com.example.Shopping.dto.ImageDto;
import com.example.Shopping.exceptions.ProductNotFoundException;
import com.example.Shopping.exceptions.ResourceNotFoundException;
import com.example.Shopping.model.Image;
import com.example.Shopping.model.Product;
import com.example.Shopping.repository.ImageRepository;
import com.example.Shopping.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
//    @Autowired
//    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    public Image getImageById(Long id) throws ResourceNotFoundException {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
    }

    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            try {
                throw new ResourceNotFoundException("No image found with id: " + id);
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

    }
    public Image updateImage(MultipartFile file,Long id){
        try {
            Image image=getImageById(id);
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
imageRepository.save(image);
return image;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) throws ProductNotFoundException {
        //TODO
        Product product=productService.getProductById(productId);
        List<ImageDto> list=new ArrayList<>();
        for(MultipartFile file:files){
            Image img=new Image();
            img.setFileName(file.getOriginalFilename());
            img.setFileType(file.getContentType());
            try {
                img.setImage(new SerialBlob(file.getBytes()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            img.setProduct(product);


            String buildDownloadUrl = "/api/v1/images/image/download/";
            String downloadUrl = buildDownloadUrl+img.getId();
            img.setDownloadUrl(downloadUrl);
            Image savedImage = imageRepository.save(img);

            savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
            imageRepository.save(savedImage);

            ImageDto imageDto = new ImageDto();
            imageDto.setId(savedImage.getId());
            imageDto.setFileName(savedImage.getFileName());
            imageDto.setDownloadUrl(savedImage.getDownloadUrl());
            list.add(imageDto);


        }


        return list;
    }

}
