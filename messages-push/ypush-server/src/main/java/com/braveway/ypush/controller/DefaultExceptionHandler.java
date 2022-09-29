package com.braveway.ypush.controller;

import com.braveway.ypush.api.push.PushResult;
import com.braveway.ypush.exception.YpushException;
import com.braveway.ypush.api.push.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    /**缺少必要的参数*/
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public PushResult missingParameterHandler(HttpServletRequest request, MissingServletRequestParameterException e) {
        this.logError(request,e);
        return  PushResult.fail(ErrorCode.MISS_PARAM);
    }

    /**参数类型不匹配*/
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public PushResult methodArgumentTypeMismatchException(HttpServletRequest request,MethodArgumentTypeMismatchException e) {
        this.logError(request,e);
        return PushResult.fail(ErrorCode.MISMATCH_TYPE);
    }

    /**不支持的请求方法*/
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public PushResult httpRequestMethodNotSupportedException(HttpServletRequest request,HttpRequestMethodNotSupportedException e) {
        this.logError(request,e);
        return PushResult.fail(ErrorCode.METHOD_NOT_SUPPORTED);
    }

    /**参数错误*/
    @ExceptionHandler(value = IllegalArgumentException.class)
    public PushResult illegalArgumentException(HttpServletRequest request,IllegalArgumentException e) {
        this.logError(request,e);
        return PushResult.fail(ErrorCode.ILLEGAL_ARGUMENT);
    }

    /**业务异常处理*/
    @ExceptionHandler(value = YpushException.class)
    public PushResult businessException(HttpServletRequest request, YpushException e) {
        log.error("path:{}, queryParam:{},errorCode:{} message:{}", request.getRequestURI(), request.getQueryString(),
                e.getErrorCode(),e.getErrorMsg(), e);
        PushResult res = new PushResult();
        res.setResultCode(e.getErrorCode());
        res.setMessage(e.getErrorMsg());
        return res;
    }

    /**其他异常统一处理*/
    @ExceptionHandler(value = Exception.class)
    public PushResult exception(HttpServletRequest request, Exception e) {
        this.logError(request,e);
        return PushResult.fail(ErrorCode.SERVER_EXCEPTION);
    }

    /**
     * 记录错误日志
     */
    private void logError(HttpServletRequest request, Exception e){
        log.error("path:{}, queryParam:{}, errorMessage:{}", request.getRequestURI(), request.getQueryString(), e.getMessage(), e);
    }
}
