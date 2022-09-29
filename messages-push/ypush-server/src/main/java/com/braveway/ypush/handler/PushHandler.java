package com.braveway.ypush.handler;

import com.braveway.ypush.api.push.request.BaseRequest;

/**
 * @BelongsProject: messages-push
 * @BelongsPackage: com.braveway.ypush.handler
 * @Author: YEJUN
 * @CreateTime: 2022-06-13  16:24
 * @Description: 推送处理接口
 * @Version: 1.0
 */
public  interface PushHandler<R extends BaseRequest> {

    void handle(R param);
}
