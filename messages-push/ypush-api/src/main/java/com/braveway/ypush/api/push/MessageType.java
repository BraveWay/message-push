package com.braveway.ypush.api.push;

public enum MessageType {

    WEB_SOCKET("websocket",MessagePlatformEnum.WEB_SOCKET),

    // ================================钉钉-群机器人====================================
    DING_TALK_ROBOT_TEXT("文本", MessagePlatformEnum.DING_TALK_ROBOT),
    DING_TALK_ROBOT_MARKDOWN("Markdown", MessagePlatformEnum.DING_TALK_ROBOT),
    DING_TALK_ROBOT_LINK("链接消息", MessagePlatformEnum.DING_TALK_ROBOT),
    DING_TALK_ROBOT_ACTION_CARD_SINGLE("卡片-单按钮", MessagePlatformEnum.DING_TALK_ROBOT),
    DING_TALK_ROBOT_ACTION_CARD_MULTI("卡片-多按钮", MessagePlatformEnum.DING_TALK_ROBOT),
    DING_TALK_ROBOT_FEED_CARD("FeedCard", MessagePlatformEnum.DING_TALK_ROBOT);

    /**
     * 所属平台
     */
    private final MessagePlatformEnum platform;
    private final String name;

    MessageType(String name, MessagePlatformEnum platform) {
        this.name = name;
        this.platform = platform;
    }

    public MessagePlatformEnum getPlatform() {
        return platform;
    }

    public String getName() {
        return name;
    }
}
