package base.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 自定义响应结构
 */
@Data
public class ResultUtils {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public ResultUtils() {
    }
    public ResultUtils(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }
    public ResultUtils(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public ResultUtils(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultUtils ok() {
        return new ResultUtils(null);
    }
    public static ResultUtils ok(String message) {
        return new ResultUtils(message, null);
    }
    public static ResultUtils ok(Object data) {
        return new ResultUtils(data);
    }
    public static ResultUtils ok(String message, Object data) {
        return new ResultUtils(message, data);
    }

    public static ResultUtils build(Integer code, String message) {
        return new ResultUtils(code, message, null);
    }

    public static ResultUtils build(Integer code, String message, Object data) {
        return new ResultUtils(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }


    /**
     * JSON字符串转成 MengxueguResult 对象
     * @param json
     * @return
     */
    public static ResultUtils format(String json) {
        try {
            return JSON.parseObject(json, ResultUtils.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
