package com.braveway.ypush.api.push;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class PushResult implements Serializable {
    public int resultCode;

    public String message;

    public String userId;
    public Object[] timeLine;

    public PushResult(int resultCode) {
        this.resultCode = resultCode;
    }

    public PushResult(int resultCode, String msg) {
    }

    public PushResult(ErrorCode errorCode, String msg) {
    }

    public PushResult() {
    }

    public int getResultCode() {
        return resultCode;
    }

    public PushResult setResultCode(int resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public PushResult setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Object[] getTimeLine() {
        return timeLine;
    }

    public PushResult setTimeLine(Object[] timeLine) {
        this.timeLine = timeLine;
        return this;
    }

    public static PushResult success(){
        PushResult pushResult =  new PushResult();
        return pushResult.setResultCode(0);
    }

    public  static PushResult fail(ErrorCode errorCode){
        return new PushResult(errorCode.getCode(),errorCode.getMsg());
    }
}
