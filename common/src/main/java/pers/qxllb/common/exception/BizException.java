package pers.qxllb.common.exception;

/**
 * 公共业务异常定义
 *
 * @author liangb
 * @version 1.0
 * @date 2021/2/15 18:20
 */
public class BizException extends RuntimeException {

    /**
     * 错误码定义
     */
    private Integer code;

    /**
     * 错误码信息
     */
    private String message;

    /**
     * 构造函数
     * @param code 错误码
     * @param message 错误信息
     */
    public BizException(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    /**
     * 获取错误码
     */
    public Integer getCode(){
        return this.code;
    }

    /**
     * 获取错误信息
     * @return String
     */
    @Override
    public String getMessage(){
        return this.message;
    }

}
