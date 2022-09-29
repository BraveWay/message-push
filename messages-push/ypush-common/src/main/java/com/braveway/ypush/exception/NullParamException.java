package com.braveway.ypush.exception;

import com.braveway.ypush.api.push.ErrorCode;

public class NullParamException extends  YpushException{

    public NullParamException() {
        super(ErrorCode.NULL_PARAM.errorCode, ErrorCode.NULL_PARAM.errorMsg);
    }
}
