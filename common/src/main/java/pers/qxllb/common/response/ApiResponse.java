package pers.qxllb.common.response;

import lombok.Data;

/**
 * API接口结果对象
 *
 * @author liangb
 * @version 1.0
 * @date 2021/2/15 22:08
 */
@Data
public class ApiResponse<T> extends BaseResponse {

    private T data;

}
