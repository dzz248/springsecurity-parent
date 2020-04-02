package springsecurity.authentication.mobile;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * 一般是第三方短息服务
 **/
@Log4j2
public class SmsCodeSender implements SmsSend {
    @Override
    public boolean sendSms(String mobile, String msg) {
        String content = String.format("短信验证平台验证码：%s,请勿泄露他人",msg);


        log.info("手机号：" + mobile + "，短信：" + content);

        return false;
    }
}
