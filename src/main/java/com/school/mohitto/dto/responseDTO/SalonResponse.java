package com.school.mohitto.dto.responseDTO;

import com.school.mohitto.domain.Salon;

public record SalonResponse(
        Long id,
        String name,
        String description,
        String address,
        String telephone,
        String link
) {
    public static SalonResponse from(Salon salon) {
        return new SalonResponse(
                salon.getId(),
                salon.getName(),
                salon.getDescription(),
                salon.getAddress(),
                salon.getTelephone(),
                salon.getLink()
        );
    }
}
