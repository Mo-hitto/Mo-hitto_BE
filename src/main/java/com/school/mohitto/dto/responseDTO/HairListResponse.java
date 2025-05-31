package com.school.mohitto.dto.responseDTO;

import com.school.mohitto.domain.Hair;
import org.springframework.data.domain.Page;

import java.util.List;

public record HairListResponse(
        List<HairResponse> hairs,
        int currentPage,
        int totalPages,
        long totalElements
) {
    public static HairListResponse from(Page<Hair> page) {
        return new HairListResponse(
                page.stream().map(HairResponse::from).toList(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}
