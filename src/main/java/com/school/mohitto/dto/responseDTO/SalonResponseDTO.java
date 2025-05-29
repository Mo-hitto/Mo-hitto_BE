package com.school.mohitto.dto.responseDTO;

import com.school.mohitto.domain.Salon;

public record SalonResponseDTO(
        Long id,
        String name,
        String description,
        String address,
        String telephone,
        String link
) {
    public static SalonResponseDTO from(Salon salon) {
        return new SalonResponseDTO(
                salon.getId(),
                salon.getName(),
                salon.getDescription(),
                salon.getAddress(),
                salon.getTelephone(),
                salon.getLink()
        );
    }
}
