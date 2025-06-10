package com.school.mohitto.dto.responseDTO;

public record LikeToggleResponse(
        Long hairId,
        String imageUrl,
        boolean liked
) {}
