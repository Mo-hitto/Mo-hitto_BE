package com.school.mohitto.dto.responseDTO;

public record ImageDeletedResponse(Integer isSuccess) {
    public static ImageDeletedResponse of(Integer isSuccess) {
        return new ImageDeletedResponse(isSuccess);
    }
}
