package com.school.mohitto.controller;

import com.school.mohitto.aws.s3.S3Uploader;
import com.school.mohitto.domain.UploadImage;
import com.school.mohitto.dto.responseDTO.ImageDeletedResponse;
import com.school.mohitto.dto.responseDTO.UploadImageResponse;
import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import com.school.mohitto.repository.UploadImageRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@Tag(name = "Image", description = "이미지 업로드 테스트 관련 API")
public class ImageController {

    private final S3Uploader s3Uploader;
    private final UploadImageRepository uploadImageRepository;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public UploadImageResponse create(@RequestPart(value = "image", required = false)
                                      MultipartFile multipartFile) throws IOException {
        String fileName = "";
        if (multipartFile == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        fileName = s3Uploader.upload(multipartFile, "face"); // S3 버킷의 images 디렉토리 안에 저장됨
        System.out.println("fileName = " + fileName);
        UploadImage image = new UploadImage(fileName);
        uploadImageRepository.save(image);
        return UploadImageResponse.of(image.getId(),image.getUploadImageUrl());
    }

    @DeleteMapping("/delete")
    @Transactional
    public ImageDeletedResponse imageDelete(@RequestParam(name = "imageUrl") String url){
        s3Uploader.deleteByUrl(url);
        Integer isSuccess = uploadImageRepository.deleteByUploadImageUrl(url);
        if(isSuccess == 0){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        return ImageDeletedResponse.of(isSuccess);
    }
}



