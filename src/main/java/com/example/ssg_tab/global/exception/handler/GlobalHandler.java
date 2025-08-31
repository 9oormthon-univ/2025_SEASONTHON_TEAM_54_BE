package com.example.ssg_tab.global.exception.handler;

import com.example.ssg_tab.global.apiPayload.code.BaseErrorCode;
import com.example.ssg_tab.global.exception.GeneralException;

public class GlobalHandler extends GeneralException {

    public GlobalHandler(BaseErrorCode code) {
        super(code);
    }

}
