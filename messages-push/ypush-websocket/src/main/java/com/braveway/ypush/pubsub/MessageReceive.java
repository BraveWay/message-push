package com.braveway.ypush.pubsub;

import com.braveway.ypush.config.NettyConfig;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageReceive {

    private final Logger logger=  LoggerFactory.getLogger(getClass());

    /**
     * 订阅消息,发送给指定用户
     * @param object
     */
    public void getMessageToOne(String object) {
        Jackson2JsonRedisSerializer serializer = getSerializer(NettyPushMessageBody.class);
        NettyPushMessageBody pushMessageBody = (NettyPushMessageBody) serializer.deserialize(object.getBytes());
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("get server host Exception e:", e);
        }
        logger.info("我的ip:{}订阅消息,发送给指定用户：",ip,pushMessageBody.toString());

        // 推送消息
        String message = pushMessageBody.getMessage();
        String userId = pushMessageBody.getUserId();
        ConcurrentHashMap<String, Channel> userChannelMap = NettyConfig.getUserChannelMap();
        Channel channel = userChannelMap.get(userId);
        if(!Objects.isNull(channel)){
            // 如果该用户的客户端是与本服务器建立的channel,直接推送消息
            channel.writeAndFlush(new TextWebSocketFrame(message));
        }
    }

    /**
     * 订阅消息，发送给所有用户
     * @param object
     */
    public void getMessageToAll(String object) {
        Jackson2JsonRedisSerializer serializer = getSerializer(String.class);
        String message = (String) serializer.deserialize(object.getBytes());
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("get server host Exception e:", e);
        }
        logger.info("我的ip:"+ ip +",订阅消息,发送给所有用户：" + message);
        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(message));
    }

    private Jackson2JsonRedisSerializer getSerializer(Class clazz){
        //序列化对象（特别注意：发布的时候需要设置序列化；订阅方也需要设置序列化）
        Jackson2JsonRedisSerializer serial = new Jackson2JsonRedisSerializer(clazz);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        serial.setObjectMapper(objectMapper);
        return serial;
    }
}
