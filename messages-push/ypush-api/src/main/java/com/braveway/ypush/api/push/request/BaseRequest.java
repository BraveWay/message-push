package com.braveway.ypush.api.push.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @BelongsProject: messages-push
 * @BelongsPackage: com.braveway.ypush.api.push
 * @Author: YEJUN
 * @CreateTime: 2022-06-12  14:31
 * @Description: 参数超类
 * @Version: 1.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class BaseRequest implements Serializable {
    /***请求编号*/
    private String requestNo;
}
