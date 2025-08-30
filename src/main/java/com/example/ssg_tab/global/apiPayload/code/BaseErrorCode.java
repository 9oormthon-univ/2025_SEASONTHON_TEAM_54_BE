package com.example.ssg_tab.global.apiPayload.code;

import com.example.ssg_tab.global.apiPayload.dto.ErrorReasonDTO;

public interface BaseErrorCode {
    ErrorReasonDTO getReason();
    ErrorReasonDTO getReasonHttpStatus();
}
