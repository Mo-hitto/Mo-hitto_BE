package com.school.mohitto.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST,  "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,  "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,  "금지된 요청입니다."),
    INVALID_REQUEST_INFO(HttpStatus.BAD_REQUEST,  "요청된 정보가 올바르지 않습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검증에 실패했습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,  "유효하지 않은 파라미터입니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(HttpStatus.BAD_REQUEST,"Enum Type이 일치하지 않아 Binding에 실패하였습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED,"지원하지 않는 HTTP method 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,  "서버 에러, 관리자에게 문의 바랍니다."),

    // JWT 토큰 에러코드
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효한 토큰이 아닙니다."),
    INVALID_TOKEN_ALREADY_LOGOUT(HttpStatus.BAD_REQUEST,"로그아웃되었습니다."),
    INVALID_TOKEN_BEARER_TYPE(HttpStatus.UNAUTHORIZED, "Bearer 타입이 아닙니다."),
    TOKEN_EMPTY(HttpStatus.UNAUTHORIZED,"토큰이 비어있습니다."),
    UNDEFINED_USER_TOKEN(HttpStatus.BAD_REQUEST,"존재하지 않는 유저의 토큰입니다."),

    // OAuth2 관련 에러코드
    UNSUPPORTED_OAUTH2_TYPE(HttpStatus.BAD_REQUEST,"지원하는 OAuth2 타입이 아닙니다."),

    // Security 관련 에러코드
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED,"로그인이 필요한 기능입니다."),

    // 진단 에러코드
    DIAGNOSIS_NOT_FOUND(HttpStatus.NOT_FOUND, "진단 정보를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "유저 정보를 찾을 수 없습니다."),
    GENDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "유효하지 않은 성별 ID입니다."),
    HAIR_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "유효하지 않은 모발 형태 ID입니다."),
    HAIR_LENGTH_NOT_FOUND(HttpStatus.BAD_REQUEST, "유효하지 않은 기장 ID입니다."),
    FOREHEAD_SHAPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "유효하지 않은 이마 형태 ID입니다."),
    CHECKBONE_SHAPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "유효하지 않은 광대 형태 ID입니다."),

    // image 저장 관련 에러
    FILE_UPLOAD_FAIL(HttpStatus.BAD_REQUEST,  "잘못된 요청입니다.")

    ;

    private final HttpStatus status;
    private final String message;
}
