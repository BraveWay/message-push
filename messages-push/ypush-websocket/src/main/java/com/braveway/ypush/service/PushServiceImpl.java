package com.braveway.ypush.service;


import com.braveway.ypush.constant.BaseConstant;
import com.braveway.ypush.pubsub.NettyPushMessageBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PushServiceImpl implements PushService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void pushMsgToOne(String userId, String msg){
        // 发布，给其他服务器消费
        NettyPushMessageBody pushMessageBody = new NettyPushMessageBody();
        pushMessageBody.setUserId(userId);
        pushMessageBody.setMessage(msg);
        log.info("Redis订阅模式：{}",pushMessageBody.toString());
        redisTemplate.convertAndSend(BaseConstant.PUSH_MESSAGE_TO_ONE,pushMessageBody);

    }
    @Override
    public void pushMsgToAll(String msg){
        // 发布，给其他服务器消费
        redisTemplate.convertAndSend(BaseConstant.PUSH_MESSAGE_TO_ALL,msg);
    }
}
