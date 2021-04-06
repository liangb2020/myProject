package pers.qxllb.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/2/15 21:44
 */
@Data
public class BaseResponse implements Serializable {

    /**
     * 响应状态码
     */
    private Integer status;

    /**
     * 响应结果信息
     */
    private String message;
}
