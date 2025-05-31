package com.school.mohitto.dto.requestDTO;

public record ChangeFaceRequest(
        String user_image_url,
        String ref_image_url
) {
}
