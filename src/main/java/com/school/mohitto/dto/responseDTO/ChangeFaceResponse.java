package com.school.mohitto.dto.responseDTO;

import org.springframework.web.multipart.MultipartFile;

public record ChangeFaceResponse(
    MultipartFile multipartFile
) {
}
