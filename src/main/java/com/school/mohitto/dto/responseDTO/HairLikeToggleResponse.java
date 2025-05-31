package com.school.mohitto.dto.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record HairLikeToggleResponse(
        @Schema(description = "좋아요 상태가 true로 바뀐 헤어 ID (false면 null)")
        Long likedHairId
) {}
