package com.braveway.ypush.api.push.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * @BelongsProject: messages-push
 * @BelongsPackage: com.braveway.ypush.api.push
 * @Author: YEJUN
 * @CreateTime: 2022-06-12  15:32
 * @Description: websocket消息推送请求体
 * @Version: 1.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketRequest extends BaseRequest {

    /**消息内容*/
    private String message;

    /**是否广播全网在线用户*/
    private boolean broadcast = false;

    /**用户ID*/
    private String userId;

    /**发送多个用户时用户ID集合*/
    private Set<String> userIdSet;


}
