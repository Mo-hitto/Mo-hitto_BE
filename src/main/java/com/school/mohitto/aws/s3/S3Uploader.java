package com.school.mohitto.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3Uploader {

    private final AmazonS3 amazonS3Client;
    private final S3Properties properties;
    private static final Logger logger = LoggerFactory.getLogger(S3Uploader.class);

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));

        return upload(uploadFile, dirName);
    }

    // 파일 이름을 UUID로 변경
    private String generateUUIDFilename(File file) {
        String originalFilename = file.getName();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuidFilename = java.util.UUID.randomUUID().toString() + extension;
        log.info("파일 이름 변경 : {}", uuidFilename);
        return uuidFilename;
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + generateUUIDFilename(uploadFile);
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    // s3에 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(properties.bucket(), fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );

        return amazonS3Client.getUrl(properties.bucket(), fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    // MultipartFile -> File 로 변환
    private Optional<File> convert(MultipartFile file) {
        try {
            File convertFile = new File(file.getOriginalFilename());

            if (convertFile.createNewFile() || convertFile.isFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }
                return Optional.of(convertFile);
            } else {
                log.error("파일 생성에 실패했습니다. 파일이 이미 존재합니다: {}", convertFile.getName());
                return Optional.empty();
            }
        } catch (IOException e) {
            log.error("MultipartFile을 File로 변환하는데 실패했습니다.", e);
            return Optional.empty();
        }
    }

    // S3에서 파일 삭제
    public void delete(String dirName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(properties.bucket(), dirName));
        log.info("S3 bucket에서 File {} 삭제.", dirName);
    }

    public void deleteByUrl(String imageUrl) {
        String bucketUrl = "https://" + properties.bucket() + ".s3." + properties.region() + ".amazonaws.com/";

        if (!imageUrl.startsWith(bucketUrl)) {
            throw new IllegalArgumentException("올바르지 않은 S3 이미지 URL입니다.");
        }

        // S3 키 추출 (예: face/abc123.png)
        String fileKey = imageUrl.substring(bucketUrl.length());

        // 실제 삭제 수행
        delete(fileKey); // 기존 delete(String fileKey) 함수 사용
    }
}
