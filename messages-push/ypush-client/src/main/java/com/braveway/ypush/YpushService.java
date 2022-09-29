package com.braveway.ypush;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.lang.Singleton;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.braveway.ypush.api.push.*;
import com.braveway.ypush.api.push.request.BaseRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public final class YpushService {

    /**
     * 路由服务名称
     */
    private static final String RPUSH_ROUTE_SERVICE_NAME = "rpush-route";

    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    private static final String GRANT_TYPE_PASSWORD = "password";

    private final String baseUrl;
    /**
     * 授权方式，client_credentials或者password（如果为空，则认为是外部维护的accessToken）
     */
    private String grantType;
    /**
     * 获取accessToke的url
     */
    private String accessTokenUrl;

    public static TimedCache<String, String> accessTokenTimedCache;

    /**
     * 如果由外部程序维护accessToken，可以用这个方法构造投递器
     *
     * @param baseUrl Rpush服务路径，http://ip:port
     */
    public static YpushService instance(String baseUrl) {
        return Singleton.get(YpushService.class, baseUrl);
    }

    /**
     * 如果是client_credentials的授权方式，用这个方法构造消息sdk
     *
     * @param baseUrl      Rpush服务路径，http://ip:port
     * @param clientId     clientId
     * @param clientSecret clientSecret
     */
    public static YpushService instance(String baseUrl, String clientId, String clientSecret) {
        return Singleton.get(YpushService.class, baseUrl, clientId, clientSecret);
    }

    public static YpushService instance(String clientId, String clientSecret) {
        return Singleton.get(YpushService.class, clientId, clientSecret);
    }

    /**
     * 如果是password的授权方式，用这个方法构造消息sdk
     *
     * @param baseUrl      Rpush服务路径，http://ip:port
     * @param clientId     clientId
     * @param clientSecret clientSecret
     * @param loginName    loginName
     * @param password     password
     */
    public static YpushService instance(String baseUrl, String clientId, String clientSecret, String loginName, String password) {
        return Singleton.get(YpushService.class, baseUrl, clientId, clientSecret, loginName, password);
    }

    public static YpushService instance(String clientId, String clientSecret, String loginName, String password) {
        return Singleton.get(YpushService.class, clientId, clientSecret, loginName, password);
    }


    private YpushService(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    private YpushService(String baseUrl, String clientId, String clientSecret) {
        this.baseUrl = baseUrl;

        this.grantType = GRANT_TYPE_CLIENT_CREDENTIALS;
        this.accessTokenUrl = baseUrl + "/" + RPUSH_ROUTE_SERVICE_NAME + "/oauth/token?grant_type=" + grantType + "&scope=all&client_id=" + clientId + "&client_secret=" + clientSecret;
    }

    private YpushService(String baseUrl, String clientId, String clientSecret, String loginName, String password) {
        this.baseUrl = baseUrl;

        this.grantType = GRANT_TYPE_PASSWORD;
        this.accessTokenUrl = baseUrl + "/" + RPUSH_ROUTE_SERVICE_NAME + "/oauth/token?grant_type=" + grantType + "&scope=all&client_id="
                + clientId + "&client_secret=" + clientSecret
                + "&username=" + loginName + "&password=" + password;
    }


    public FutureTask<PushResult> pushMsg(PushContext context) {
        FutureTask<PushResult> future = new FutureTask<>(new Callable<PushResult>() {
            @Override
            public PushResult call() throws Exception {
                String url = null;
                if (context.isBroadcast()) {
                    url = baseUrl + "/push/pushAll";
                } else {
                    url = baseUrl + "/push/pushOne";
                }
                JSONObject body = new JSONObject(context);
                HttpResponse httpResponse = HttpRequest.post(url)
                        .body(body.toString())
                        .timeout(10000)//超时，毫秒
                        .executeAsync();
                int status = httpResponse.getStatus();
                if (HttpStatus.HTTP_OK == status) {
                    return JSONUtil.toBean(httpResponse.body(), PushResult.class);
                }
                return PushResult.fail(ErrorCode.SERVER_EXCEPTION);
            }
        });
//        ///托管给线程池处理
        Future<?> futureResult = Executors.newCachedThreadPool().submit(future);
//        futureResult.
//        //托管给单独线程处理
//        new Thread(future).start();
        return future;
    }

    public FutureTask<PushResult> pushAsync(BaseRequest baseRequest) {
        FutureTask<PushResult> future = new FutureTask<>(new Callable<PushResult>() {
            @Override
            public PushResult call() throws Exception {
                MessageType messageType = YPushMessage.MESSAGE_TYPE_PARAM_MAP.get(baseRequest.getClass());

                Map<String, Object> params = new HashMap<>();
                Map<String, Object> param = new HashMap<>();
                param.put("param", baseRequest);
                params.put(messageType.name(), param);

                JSONObject body = new JSONObject();
                body.put("messageParam", params);

                String url = baseUrl + "/ypush/pushMessage";
                HttpResponse httpResponse = HttpRequest.post(url)
                        .body(body.toString())
                        .timeout(10000)//超时，毫秒
                        .executeAsync();
                int status = httpResponse.getStatus();
                if (HttpStatus.HTTP_OK == status) {
                    return JSONUtil.toBean(httpResponse.body(), PushResult.class);
                }
                return PushResult.fail(ErrorCode.SERVER_EXCEPTION);
            }
        });
        Future<?> futureResult = Executors.newCachedThreadPool().submit(future);
        return future;
    }
}
