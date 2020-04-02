package springsecurity.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import springsecurity.authentication.mobile.SmsSend;

/**
 *
 **/
@Log4j2
//@Component
public class MobileSmsCodeSender implements SmsSend {
    @Override
    public boolean sendSms(String mobile, String msg) {
       log.info("WEB应用新的短信验证码接口。向手机号："+mobile+"发送了验证码"+msg);
        return false;
    }
}
