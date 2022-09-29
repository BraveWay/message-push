package com.braveway.ypush.controller;

import cn.hutool.json.JSONObject;
import com.braveway.ypush.api.push.ErrorCode;
import com.braveway.ypush.api.push.MessageType;
import com.braveway.ypush.api.push.PushResult;
import com.braveway.ypush.api.push.YPushMessage;
import com.braveway.ypush.factory.HandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;

/**
 * @BelongsProject: messages-push
 * @BelongsPackage: com.braveway.ypush.controller
 * @Author: YEJUN
 * @CreateTime: 2022-06-12  15:39
 * @Description: 向外提供的接口，用于接受消息推送
 * @Version: 1.0
 */
@RestController
@RequestMapping("/ypush")
public class YpushController {

    @Autowired
    private HandlerFactory handlerFactory;

    @PostMapping("/pushMessage")
    public PushResult pushMessage(@RequestBody String param) {

        JSONObject json = new JSONObject(param);
        JSONObject messageParam = json.getJSONObject("messageParam");
        if (messageParam == null) {
            return PushResult.fail(ErrorCode.MISS_PARAM);
        }
        MessageType[] values = MessageType.values();
        for (MessageType value : values) {
            JSONObject jsonObject = messageParam.getJSONObject(value.name());
            if (jsonObject == null) {
                continue;
            }
            if (!YPushMessage.MESSAGE_TYPE_PARAM_MAP.containsValue(value)) {
                return PushResult.fail(ErrorCode.ILLEGAL_ARGUMENT);
            }
            YPushMessage.MESSAGE_TYPE_PARAM_MAP.forEach((k, v) -> {
                if (v.equals(value)) {
                    JSONObject object = jsonObject.getJSONObject("param");
                    handlerFactory.getHandler(k).handle(object.toBean((Type) k));
                }
            });
        }
        return PushResult.success();
    }

}
