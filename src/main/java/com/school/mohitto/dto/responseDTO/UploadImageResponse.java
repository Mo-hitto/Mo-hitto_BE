package com.school.mohitto.dto.responseDTO;

public record UploadImageResponse(Long imageId, String imageUrl) {
    public static UploadImageResponse of(Long imageId, String imageUrl) {
        return new UploadImageResponse(imageId, imageUrl);
    }
}
