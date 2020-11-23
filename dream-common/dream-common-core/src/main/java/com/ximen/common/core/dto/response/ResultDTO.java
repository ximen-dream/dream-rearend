package com.ximen.common.core.dto.response;


import com.ximen.common.core.code.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * @author zhishun.cai
 * @date 2020/8/3 19:55
 * @note 结果DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
    /**
     * 是否成功
     */
    private Boolean flag;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public ResultDTO(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultDTO(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public ResultDTO(ResponseCode responseCode) {
        this.flag = responseCode.isFlag();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public ResultDTO(Object obj) {
        if(obj != null && obj.getClass().isEnum()){
            try{
                Class<?> zClass = obj.getClass();
                Method isFlagMethod = zClass.getMethod("isFlag");
                Method getCodeMethod = zClass.getMethod("getCode");
                Method getMessageMethod = zClass.getMethod("getMessage");
                this.flag = (Boolean) isFlagMethod.invoke(obj);
                this.code = (Integer) getCodeMethod.invoke(obj);
                this.message = (String) getMessageMethod.invoke(obj);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ResultDTO(Object obj,Object data) {
        if(obj != null && obj.getClass().isEnum()){
            try{
                Class<?> zClass = obj.getClass();
                Method isFlagMethod = zClass.getMethod("isFlag");
                Method getCodeMethod = zClass.getMethod("getCode");
                Method getMessageMethod = zClass.getMethod("getMessage");
                this.flag = (Boolean) isFlagMethod.invoke(obj);
                this.code = (Integer) getCodeMethod.invoke(obj);
                this.message = (String) getMessageMethod.invoke(obj);
                this.data = data;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ResultDTO(ResponseCode baseResponseCode, Object obj) {
        this.flag = baseResponseCode.isFlag();
        this.code = baseResponseCode.getCode();
        this.message = baseResponseCode.getMessage();
        this.data = obj;
    }

}
