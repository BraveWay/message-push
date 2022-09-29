package com.braveway.ypush.pubsub;


import java.io.Serializable;

/**
 * @author sixiaojie
 * @date 2020-08-24-13:51
 */
public class NettyPushMessageBody implements Serializable {

    private String userId;

    private String message;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NettyPushMessageBody{" +
                "userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
