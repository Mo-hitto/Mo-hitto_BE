package com.school.mohitto.dto.responseDTO;

import com.school.mohitto.domain.Hair;

public record HairResponse(
        Long id,
        String name,
        String imageUrl
) {
    public static HairResponse from(Hair hair) {
        return new HairResponse(
                hair.getId(),
                hair.getName(),
                hair.getModelImage().getUploadImageUrl()
        );
    }
}
