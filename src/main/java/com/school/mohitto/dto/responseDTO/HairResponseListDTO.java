package com.school.mohitto.dto.responseDTO;

import com.school.mohitto.domain.Hair;
import org.springframework.data.domain.Page;

import java.util.List;

public record HairResponseListDTO(
        List<HairResponseDTO> hairs,
        int currentPage,
        int totalPages,
        long totalElements
) {
    public static HairResponseListDTO from(Page<Hair> page) {
        return new HairResponseListDTO(
                page.stream().map(HairResponseDTO::from).toList(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}

