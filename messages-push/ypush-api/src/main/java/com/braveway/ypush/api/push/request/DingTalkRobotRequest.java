package com.braveway.ypush.api.push.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @BelongsProject: messages-push
 * @BelongsPackage: com.braveway.ypush.api.push
 * @Author: YEJUN
 * @CreateTime: 2022-06-13  16:29
 * @Description: 钉钉机器人推送请求体
 * @Version: 1.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DingTalkRobotRequest extends BaseRequest {

    private String ssss;
}
