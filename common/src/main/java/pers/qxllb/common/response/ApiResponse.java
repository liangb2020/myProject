package pers.qxllb.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * API接口结果对象
 *
 * @author liangb
 * @version 1.0
 * @date 2021/2/15 22:08
 */
@Data
@NoArgsConstructor
public class ApiResponse<T> extends BaseResponse {

    /**
     * 响应数据对象（泛型）
     */
    private T data;

    /**
     * 构造函数
     * @param status
     * @param message
     * @param data
     */
    public ApiResponse(Integer status, String message, T data) {
        this.setStatus(status);
        this.setMessage(message);
        this.setData(data);
    }

    /**
     * 返回成功对象封装
     * @param data
     */
    public ApiResponse(T data){
        this.setStatus(CommonCode.SUCCESS.getCode());
        this.setMessage(CommonCode.SUCCESS.getMessage());
        this.setData(data);
    }

    /**
     * 返回错误对象的封装
     * @param status
     * @param message
     */
    public ApiResponse(Integer status,String message){
        this(status,message,null);
    }

    /**
     * 错误信息的构造函数
     * @param status
     * @param message
     * @param <T>
     * @return
     */
    public static  <T> ApiResponse<T> error(Integer status, String message){
        return new ApiResponse(status,message);
    }

    /**
     * 成功对象有数据返回封装
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(data);
    }

    /**
     * 成功对象无参返回封装
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> success(){
        return success(null);
    }

}
