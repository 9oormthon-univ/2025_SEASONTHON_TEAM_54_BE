package com.example.ssg_tab.global.apiPayload.status;

import com.example.ssg_tab.global.apiPayload.code.BaseErrorCode;
import com.example.ssg_tab.global.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // HTTP/요청 포맷 관련
    _METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON405", "허용되지 않은 메서드입니다."),
    _UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "COMMON415", "지원하지 않는 Content-Type 입니다."),
    _NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "COMMON406", "요청한 Accept 타입으로 응답할 수 없습니다."),
    _MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "COMMON4001", "요청 본문을 해석할 수 없습니다."),
    _TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "COMMON4002", "파라미터 타입이 올바르지 않습니다."),
    _MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON4003", "필수 파라미터가 누락되었습니다."),
    _VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "COMMON4004", "입력값 검증에 실패했습니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "요청한 자원을 찾을 수 없습니다."),
    _CONFLICT(HttpStatus.CONFLICT, "COMMON409", "요청이 서버 상태와 충돌합니다."),

    // 토큰 관련 에러
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4001", "토큰이 유효하지 않습니다."),

    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4001", "사용자가 없습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER4002","이미 가입된 이메일입니다."),
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "MEMBER4003", "비밀번호가 올바르지 않습니다."),
    ONBOARDING_STEP_ERROR(HttpStatus.BAD_REQUEST, "MEMBER4004", "사용자가 온보딩 단계가 아닙니다."),

    // 카테고리 관련 에러
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY4001","존재하지 않는 카테고리입니다."),
    INVALID_INTEREST_COUNT(HttpStatus.BAD_REQUEST, "CATEGORY4002", "카테고리는 3~5개 선택해야합니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }

    /**
     * 문자열을 안전하게 ErrorStatus로 매핑
     * - 매칭 실패 시 fallback 반환
     */
    public static ErrorStatus safeFrom(String value, ErrorStatus fallback) {
        if (value == null) return fallback;
        String key = value.trim();
        // 1. enum name 매칭
        try { return ErrorStatus.valueOf(key); } catch (Exception ignored) {}
        // 2. code 필드 매칭
        for (ErrorStatus s : values()) {
            if (s.code.equalsIgnoreCase(key)) return s;
        }
        return fallback;
    }
}
