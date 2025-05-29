package com.school.mohitto.dto.responseDTO;

import com.school.mohitto.domain.Hair;

public record HairResponseDTO(
        Long id,
        String name,
        String imageUrl
) {
    public static HairResponseDTO from(Hair hair) {
        return new HairResponseDTO(
                hair.getId(),
                hair.getName(),
                hair.getModelImage().getUploadImageUrl()
        );
    }
}
