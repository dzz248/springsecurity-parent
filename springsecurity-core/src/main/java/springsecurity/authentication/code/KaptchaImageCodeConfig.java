package springsecurity.authentication.code;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 使用第三方Kaptcha来生成图形验证码
 * @Auther: 豆 www.mengxuegu.com
 */
@Configuration
public class KaptchaImageCodeConfig {


    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_BORDER, "yes"); //图片边框
        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, "192,192,192");//边框颜色
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "110");//图片宽
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "36");//图片高
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");//字体颜色，合法值： r,g,b 或者 white,black,blue. black
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "30");//字体大小 40px
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "宋体");//字体
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");//验证码长度
        /**
         * 图片样式：水纹com.google.code.kaptcha.impl.WaterRipple
         * 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
         * 阴影com.google.code.kaptcha.impl.ShadowGimpy
         */
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
