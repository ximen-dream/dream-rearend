package com.ximen.common.exception;

/**
 * @author zhishun.cai
 * @date 2020/7/24 23:54
 * @note
 */
public class ValidateCodeException extends Exception{

    private static final long serialVersionUID = 7514854456967620043L;

    public ValidateCodeException(String message){
        super(message);
    }
}
