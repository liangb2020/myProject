package pers.qxllb.common.exception;

/**
 * 错误码接口
 *
 * @author liangb
 * @version 1.0
 * @date 2021/2/15 19:38
 */
public interface ErrorCode {

    /**
     * 获取错误码接口
     * @return
     */
    int getCode();

    /**
     * 获取错误信息接口
     * @return
     */
    String getMessage();

}
