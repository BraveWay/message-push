package com.braveway.ypush.api.push;

import com.braveway.ypush.api.push.request.DingTalkRobotRequest;
import com.braveway.ypush.api.push.request.WebSocketRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: messages-push
 * @BelongsPackage: com.braveway.ypush.api.push
 * @Author: yanhongwei
 * @CreateTime: 2022-06-13  11:11
 * @Description: TODO
 * @Version: 1.0
 */
public class YPushMessage {

    /**
     * 消息参数和消息类型的映射关系，键为消息参数类型，值为对应的消息类型
     */
   public static final Map<Class<?>, MessageType> MESSAGE_TYPE_PARAM_MAP = new HashMap<>();

    public static WebSocketRequest.WebSocketRequestBuilder<?,?> websocket(){
        return WebSocketRequest.builder();
    }
    static{
        MESSAGE_TYPE_PARAM_MAP.put(WebSocketRequest.class,MessageType.WEB_SOCKET);
    }


    public static DingTalkRobotRequest.DingTalkRobotRequestBuilder<?,?> DING_TALK_ROBOT_TEXT(){
        return DingTalkRobotRequest.builder();
    }
    static{
        MESSAGE_TYPE_PARAM_MAP.put(DingTalkRobotRequest.class,MessageType.DING_TALK_ROBOT_TEXT);
    }
}
