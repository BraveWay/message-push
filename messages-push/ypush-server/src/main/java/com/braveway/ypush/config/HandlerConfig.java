package com.braveway.ypush.config;

import com.braveway.ypush.api.push.request.BaseRequest;
import com.braveway.ypush.factory.HandlerFactory;
import com.braveway.ypush.handler.PushHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: messages-push
 * @BelongsPackage: com.braveway.ypush.config
 * @Author: YEJUN
 * @CreateTime: 2022-06-13  16:39
 * @Description: handler配置类：自动将所有的hanler放入工厂类
 * @Version: 1.0
 */
@Configuration
public class HandlerConfig {

    @Bean
    public HandlerFactory initHandlerFactory(List<PushHandler> handlerList) {

        HandlerFactory factory =  new HandlerFactory();

        Map<Class<BaseRequest>, PushHandler> strategyMap = factory.getHandlerInstances();
        for (PushHandler handler : handlerList) {
            ParameterizedType parameterizedType = (ParameterizedType) handler.getClass().getGenericInterfaces()[0];
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for(Type type : actualTypeArguments){
                Class clazz = (Class) type;
                if(BaseRequest.class.isAssignableFrom(clazz)){
                    strategyMap.put(clazz,handler);
                }
            }
        }
        return factory;
    }
}
