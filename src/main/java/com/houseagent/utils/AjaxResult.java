package com.houseagent.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("返回结果类")
public class AjaxResult {
    @ApiModelProperty("状态码")
    Integer code;
    @ApiModelProperty("封装的数据")
    private Object data;


    public AjaxResult(int code, Object data) {
        this.code = code;
        this.data = data;
    }

}

