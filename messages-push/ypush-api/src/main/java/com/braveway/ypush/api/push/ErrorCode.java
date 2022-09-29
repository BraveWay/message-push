package com.braveway.ypush.api.push;


public enum ErrorCode {

    MISS_PARAM(1,"缺少参数"),
    MISMATCH_TYPE(2,"参数类型不匹配"),
    METHOD_NOT_SUPPORTED(5,"请求方法不支持"),

    ILLEGAL_ARGUMENT(6,"参数错误"),

    NULL_PARAM(7,"参数为空"),

    SERVER_EXCEPTION(-1,"系统报错");

    ErrorCode(int code, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = (byte) code;
    }

    public final byte errorCode;
    public final String errorMsg;

    public int getCode() {
        return errorCode;
    }

    public String getMsg() {
        return errorMsg;
    }
}
