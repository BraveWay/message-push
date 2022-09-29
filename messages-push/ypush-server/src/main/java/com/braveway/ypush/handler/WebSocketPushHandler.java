package com.braveway.ypush.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.braveway.ypush.api.push.request.WebSocketRequest;
import com.braveway.ypush.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @BelongsProject: messages-push
 * @BelongsPackage: com.braveway.ypush.handler
 * @Author: YEJUN
 * @CreateTime: 2022-06-13  16:25
 * @Description: websocket消息推送处理类
 * @Version: 1.0
 */
@Component
public class WebSocketPushHandler implements PushHandler<WebSocketRequest>{

    @Autowired
    private PushService pushService;

    @Override
    public void handle(WebSocketRequest request) {
        String message = request.getMessage();
        if(request.isBroadcast()){
            pushService.pushMsgToAll(message);
        }else{
            Set<String> userIdSet =  request.getUserIdSet();
            if(CollectionUtil.isNotEmpty(userIdSet)){
                userIdSet.forEach( x ->{
                    pushService.pushMsgToOne(x,message);
                });
            }else{
               String userId = request.getUserId();
               if(!StringUtils.isEmpty(userId)){
                   pushService.pushMsgToOne(userId,message);
               }
            }
        }
    }
}
