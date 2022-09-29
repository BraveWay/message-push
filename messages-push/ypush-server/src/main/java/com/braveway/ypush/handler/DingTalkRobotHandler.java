package com.braveway.ypush.handler;

import com.braveway.ypush.api.push.request.DingTalkRobotRequest;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: messages-push
 * @BelongsPackage: com.braveway.ypush.handler
 * @Author: YEJUN
 * @CreateTime: 2022-06-13  16:30
 * @Description: 钉钉机器人消息处理超类
 * @Version: 1.0
 */
@Component
public class DingTalkRobotHandler implements PushHandler<DingTalkRobotRequest>{

    @Override
    public void handle(DingTalkRobotRequest param) {
        System.out.println("DingTalkRobotHandler.handle");
    }
}
