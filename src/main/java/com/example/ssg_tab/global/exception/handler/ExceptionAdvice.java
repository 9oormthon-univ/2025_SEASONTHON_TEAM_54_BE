package com.example.ssg_tab.global.exception.handler;

import com.example.ssg_tab.global.apiPayload.ApiResponse;
import com.example.ssg_tab.global.apiPayload.dto.ErrorReasonDTO;
import com.example.ssg_tab.global.apiPayload.status.ErrorStatus;
import com.example.ssg_tab.global.exception.GeneralException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import jakarta.validation.ConstraintViolationException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    /**
     * ConstraintViolationException 처리
     * - @Validated, @NotBlank 등 제약조건 위반 시 발생
     */
    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        String key = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("_VALIDATION_FAILED");

        ErrorStatus status = ErrorStatus.safeFrom(key, ErrorStatus._VALIDATION_FAILED);
        return handleExceptionInternalConstraint(e, status, HttpHeaders.EMPTY, request);
    }

    /**
     *  @Valid, @RequestBody 검증 실패 시 발생하는 예외 처리
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
            String msg = Optional.ofNullable(fe.getDefaultMessage()).orElse("");
            errors.merge(fe.getField(), msg, (a, b) -> a + ", " + b);
        }
        return handleExceptionInternalArgs(e, headers, ErrorStatus._VALIDATION_FAILED, request, errors);
    }

    /**
     * @RequestParam 누락
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return handleExceptionInternalArgs(
                e, headers, ErrorStatus._MISSING_PARAMETER, request,
                Map.of(e.getParameterName(), "필수 파라미터가 누락되었습니다.")
        );
    }

    /**
     * 잘못된 HTTP Method
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return handleExceptionInternalFalse(
                e, ErrorStatus._METHOD_NOT_ALLOWED, headers,
                ErrorStatus._METHOD_NOT_ALLOWED.getHttpStatus(), request, e.getMethod()
        );
    }

    /**
     * JSON 파싱 실패 / 본문 해석 불가
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return handleExceptionInternalFalse(
                e, ErrorStatus._MESSAGE_NOT_READABLE, headers,
                ErrorStatus._MESSAGE_NOT_READABLE.getHttpStatus(), request, null
        );
    }

    /**
     * 파라미터 타입 불일치
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(
            MethodArgumentTypeMismatchException e,
            WebRequest request
    ) {
        return handleExceptionInternalArgs(
                e, HttpHeaders.EMPTY, ErrorStatus._TYPE_MISMATCH, request,
                Map.of(e.getName(), "타입이 올바르지 않습니다.")
        );
    }

    /**
     * Content-Type 미지원
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return handleExceptionInternalFalse(
                e, ErrorStatus._UNSUPPORTED_MEDIA_TYPE, headers,
                ErrorStatus._UNSUPPORTED_MEDIA_TYPE.getHttpStatus(), request,
                e.getContentType() != null ? e.getContentType().toString() : null
        );
    }

    /**
     * Not Acceptable (Accept 헤더 불일치)
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return handleExceptionInternalFalse(
                e, ErrorStatus._NOT_ACCEPTABLE, headers,
                ErrorStatus._NOT_ACCEPTABLE.getHttpStatus(), request, null
        );
    }

    /**
     * 비즈니스 로직에서 직접 던지는 GeneralException 처리
     * - ErrorStatus / ErrorReasonDTO 기반 응답
     */
    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity<Object> onThrowException(GeneralException ex, HttpServletRequest request) {
        ErrorReasonDTO reason = ex.getErrorReasonHttpStatus();
        return handleExceptionInternal(ex, reason, null, request);
    }

    /**
     * 그 외 모든 예외
     */
    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        log.error("[UNHANDLED] {}", e.toString(), e);
        return handleExceptionInternalFalse(
                e, ErrorStatus._INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY,
                ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus(), request, null
        );
    }

    // 아래는 내부 공통 빌더
    /**
     * GeneralException → ErrorReasonDTO 기반 응답 생성
     */
    private ResponseEntity<Object> handleExceptionInternal(
            Exception e,
            ErrorReasonDTO reason,
            HttpHeaders headers,
            HttpServletRequest request
    ) {
        log.error("[BUSINESS] code={}, msg={}", reason.getCode(), e.getMessage(), e);
        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(), reason.getMessage(), null);
        WebRequest webRequest = new ServletWebRequest(request);
        return super.handleExceptionInternal(
                e, body, headers, reason.getHttpStatus(), webRequest
        );
    }

    /**
     * 공통 내부 서버/기타 에러 처리
     * - data 필드에 errorPoint(상황 설명) 선택적으로 포함
     */
    private ResponseEntity<Object> handleExceptionInternalFalse(
            Exception e,
            ErrorStatus errorCommonStatus,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request,
            String errorPoint
    ) {
        log.error("[ERROR] {} ({})", errorCommonStatus.getCode(), errorPoint, e);
        ApiResponse<Object> body = ApiResponse.onFailure(
                errorCommonStatus.getCode(), errorCommonStatus.getMessage(), errorPoint
        );
        return super.handleExceptionInternal(
                e, body, headers, status, request
        );
    }

    /**
     * @Valid 바인딩 에러 처리
     * - 필드명 -> 에러메시지 맵을 data에 포함
     */
    private ResponseEntity<Object> handleExceptionInternalArgs(
            Exception e,
            HttpHeaders headers,
            ErrorStatus errorCommonStatus,
            WebRequest request,
            Map<String, String> errorArgs
    ) {
        log.warn("[VALIDATION] {}", errorArgs);
        ApiResponse<Object> body = ApiResponse.onFailure(
                errorCommonStatus.getCode(), errorCommonStatus.getMessage(), errorArgs
        );
        return super.handleExceptionInternal(
                e, body, headers, errorCommonStatus.getHttpStatus(), request
        );
    }

    /**
     * ConstraintViolationException 처리
     * - 단순 메시지/상태코드만 반환
     */
    private ResponseEntity<Object> handleExceptionInternalConstraint(
            Exception e,
            ErrorStatus errorCommonStatus,
            HttpHeaders headers,
            WebRequest request
    ) {
        log.warn("[CONSTRAINT] {}", errorCommonStatus.getCode(), e);
        ApiResponse<Object> body = ApiResponse.onFailure(
                errorCommonStatus.getCode(), errorCommonStatus.getMessage(), null
        );
        return super.handleExceptionInternal(
                e, body, headers, errorCommonStatus.getHttpStatus(), request
        );
    }
}