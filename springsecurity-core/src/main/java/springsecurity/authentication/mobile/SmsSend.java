package springsecurity.authentication.mobile;

/**
 * 短信发送统一接口
 **/
public interface SmsSend {
    /**
     * 发送短信
     * @param mobile 手机号
     * @param msg  内容
     * @return
     */
    boolean sendSms(String mobile,String msg);
}
