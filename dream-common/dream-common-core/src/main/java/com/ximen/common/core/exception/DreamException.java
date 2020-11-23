package com.ximen.common.core.exception;

import com.ximen.common.core.code.ResponseCode;
import lombok.Data;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:31
 * @note
 */
@Data
public class DreamException extends Exception{

    private static final long serialVersionUID = -6916154462432027437L;
    private Integer code;
    private String message;
    private Object data;

    public DreamException(String message){
        super(message);
    }
    public DreamException(ResponseCode responseCode){
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

}