package com.ximen.common.core.exception;

import java.io.Serializable;

/**
 * @author zhishun.cai
 * @date 2020/7/24 17:11
 * @note
 */
public class DreamAuthException extends Exception implements Serializable {
    private static final long serialVersionUID = -6916154462432027437L;

    public DreamAuthException(String message){
        super(message);
    }
}
