package com.braveway.ypush.factory;

import com.braveway.ypush.api.push.request.BaseRequest;
import com.braveway.ypush.handler.PushHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @BelongsProject: messages-push
 * @BelongsPackage: com.braveway.ypush.config
 * @Author: YEJUN
 * @CreateTime: 2022-06-13  18:18
 * @Description: 处理工厂，用于获取对应的处理类
 * @Version: 1.0
 */

public class HandlerFactory {
    private final Map<Class<BaseRequest>, PushHandler> handlerInstances = new ConcurrentHashMap<>();

    public PushHandler getHandler(Class clazz) {
        PushHandler handlerInstance = handlerInstances.get(clazz);
        if (handlerInstance == null) {
            throw new RuntimeException("未定义handler");
        }
        return handlerInstance;
    }

    public Map<Class<BaseRequest>, PushHandler> getHandlerInstances(){
        return this.handlerInstances;
    }
}
