package pers.qxllb.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * http响应结果对象
 *
 * @author liangb
 * @version 1.0
 * @date 2021/2/15 21:45
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HttpResponse<T> extends BaseResponse{

    private T data;

    public HttpResponse(Integer status,String message,T data){
        this.setStatus(status);
        this.setMessage(message);
        this.setData(data);
    }

    /**
     * 返回错误码结果对象
     * @param status Integer
     * @param message String
     * @param <T> 类型
     * @return
     */
    public static <T> HttpResponse<T> error(Integer status,String message){
        return new HttpResponse<T>(status,message,null);
    }

    /**
     * 返回成功结果码，带数据
     * @param data 类型数据
     * @param <T> 类型
     * @return HttpResult<T>
     */
    public static <T> HttpResponse<T> success(T data){
        return new HttpResponse<T>(CommonCode.SUCCESS.getCode(),CommonCode.SUCCESS.getMessage(),data);
    }

    /**
     * 返回成功结果码，不带数据
     * @param <T> 类型
     * @return HttpResult<T>
     */
    public static <T> HttpResponse<T> success(){
        return success(null);
    }

}
