package com.braveway.ypush.api.push;

import cn.hutool.core.lang.Assert;
import com.braveway.ypush.api.push.config.Config;
import com.braveway.ypush.api.push.config.DingTalkRobotConfig;
import com.braveway.ypush.api.push.config.EmptyConfig;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MessagePlatformEnum {

    WEB_SOCKET(EmptyConfig.class, "ypush服务", "", "", true),

    DING_TALK_ROBOT(DingTalkRobotConfig.class, "钉钉-群机器人", "", "", true);

    private final String name;
    private final String description;
    /**
     * 校验用的正则表达式
     */
    private final String validateReg;
    private final boolean enable;
    /**
     * 配置类型
     */
    private Class<? extends Config> configType;

    MessagePlatformEnum(Class<? extends Config> configType, String name, String description, String validateReg, boolean enable) {
        this.name = name;
        this.description = description;
        this.validateReg = validateReg;
        this.enable = enable;
        this.configType = configType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getValidateReg() {
        return validateReg;
    }

    public Class<? extends Config> getConfigType() {
        return configType;
    }

    public boolean matcher(String testStr) {
        if (StringUtils.isEmpty(testStr) || StringUtils.isEmpty(validateReg)) {
            return true;
        }
        Pattern pattern = Pattern.compile(validateReg);
        Matcher matcher = pattern.matcher(testStr);
        return matcher.matches();
    }

    public void matcherThrow(String testStr) {
        Assert.isTrue(matcher(testStr), testStr + "格式不符，请检查后重试");
    }
}
