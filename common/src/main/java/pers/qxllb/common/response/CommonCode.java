package pers.qxllb.common.response;

import lombok.Getter;
import pers.qxllb.common.exception.ErrorCode;

/**
 * 公共错误码
 *
 * @author liangb
 * @version 1.0
 * @date 2021/2/15 19:43
 */
public enum CommonCode implements ErrorCode {

    /**
     * 公共错误码
     */
    SUCCESS(200000,"处理成功."),

    UNKNOWN(599900,"未知错误"),

    INTERNAL_ERROR(599901, "服务器内部错误"),

    INVALID_SERVICE_TICKET(499901, "非法登陆态"),

    UNSUPPORTED_CODEC(499902, "不支持的编码方式"),

    UNSUPPORTED_COMPRESSION(499903, "不支持的压缩方式"),

    UNSUPPORTED_ENCRYPTION(499904, "不支持的加密方式"),

    INVALID_SIGN(499905, "非法签名"),

    VERSION_TOO_LOW(499906, "版本号过低，请升级新版本"),

    NO_PARAM(499907, "缺少参数"),

    PARAM_ERROR(499908, "非法参数")


    /**
     * 业务模块错误码
     */



    ;

    @Getter
    private int code;

    @Getter
    private String message;

    CommonCode(int code,String message){
        this.code=code;
        this.message=message;
    }
}

