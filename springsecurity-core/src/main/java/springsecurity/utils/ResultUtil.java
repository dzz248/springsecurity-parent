package springsecurity.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 自定义响应结构
 */
@Data
public class ResultUtil {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public ResultUtil() {
    }

    public ResultUtil(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }

    public ResultUtil(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public ResultUtil(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultUtil ok() {
        return new ResultUtil(null);
    }

    public static ResultUtil ok(String message) {
        return new ResultUtil(message, null);
    }

    public static ResultUtil ok(Object data) {
        return new ResultUtil(data);
    }

    public static ResultUtil ok(String message, Object data) {
        return new ResultUtil(message, data);
    }

    public static ResultUtil build(Integer code, String message) {
        return new ResultUtil(code, message, null);
    }

    public static ResultUtil build(Integer code, String message, Object data) {
        return new ResultUtil(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }


    /**
     * JSON字符串转成 MengxueguResult 对象
     *
     * @param json
     * @return
     */
    public static ResultUtil format(String json) {
        try {
            return JSON.parseObject(json, ResultUtil.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
