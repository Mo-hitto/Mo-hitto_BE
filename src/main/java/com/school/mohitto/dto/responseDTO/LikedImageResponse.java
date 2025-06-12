package com.school.mohitto.dto.responseDTO;

public record LikedImageResponse(
        Long createdImageId,
        String imageUrl,
        String hairName,
        Long hairId
) {}

