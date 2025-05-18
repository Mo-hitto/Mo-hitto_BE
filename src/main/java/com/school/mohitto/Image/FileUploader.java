package com.school.mohitto.Image;

import com.school.mohitto.exception.CustomException;
import com.school.mohitto.exception.code.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUploader {
    private final String uploadDir = "/uploads";

    public String upload(MultipartFile file, String folder) {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + "/" + folder, filename);
        try {
            Files.createDirectories(path.getParent());
            file.transferTo(path);
            return "/static/" + folder + "/" + filename;
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
        }
    }
}
