import com.braveway.ypush.YpushService;
import com.braveway.ypush.api.push.YPushMessage;
import com.braveway.ypush.api.push.request.WebSocketRequest;

public class Test {

    public static void main(String[] args) {
//        try{
//
//            FutureTask<PushResult> pushResult =  YpushService.instance("http://127.0.0.1:8080").pushMsg(PushContext.build(PushMsg.build(MsgType.MESSAGE,"newmessage")).setUserId("hsweb-dev.ystyun.com-777").setBroadcast(false));
//            System.out.println("Test.main:"+pushResult.isDone());
//            System.out.println("Test.main");
//            Thread.sleep(12000);
//            System.out.println("Test.main:"+pushResult.isDone());
//            System.out.println("Test.main:"+pushResult.get());
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        WebSocketRequest request = YPushMessage.websocket().message("111").broadcast(false).userId("123456").build();
//        DingTalkRobotRequest dingTalkRobotRequest = YPushMessage.DING_TALK_ROBOT_TEXT().requestNo("111").ssss("222").build();
        YpushService.instance("http://127.0.0.1:8080").pushAsync(request);
//        System.out.println("Test.main="+result);


    }
}
