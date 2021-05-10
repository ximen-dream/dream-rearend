package com.ximen.common.core.code;

/**
 * @author zhishun.cai
 * @date 2020/8/3 20:03
 * @note 响应状态码
 */
public enum ResponseCode {
    //1 ~ 100 公共
    ERROE(false,0,"系统错误"),
    SUCCESS(true,1,"操作成功"),
    ERROR_REQUEST_PARAMS(false,2,"请求参数错误"),
    NONE_PERMISSION_ACCESS_RESOURCE(false,3,"没有权限访问该资源"),
    ILLEGAL_TOKEN(false,4,"访问令牌不合法"),
    ILLEGAL_RESOURCE_URL(false,5,"请通过网关获取资源"),
    ACCESS_TIMEOUT(false,6,"服务访问超时，请稍后再试"),
    EROOR_FEIGN_INVOKE(false,7),
    ADVICE_UPLOAD_ERROR(false,8,"图片上传失败"),
    ERROR_EXIST_EMAIL(false,9,"邮箱存在"),
    ERROR_EXIST_ROLENAME(false,10,"角色名称已存在"),
    ERROR_EXIST_USERNAME(false,11,"用户名称已存在"),
    ERROR_UNSUPPORY_SOCIAL_TYPE(false,12,"不支持该第三方登录"),
    BIND_ERROR_USER_OR_PASSWORD(false,13,"绑定系统账号失败，用户名或密码错误！"),
    ERROR_CRON_EXPRESSION(false,14,"Cron表达式错误！"),
    ;
    private boolean flag;

    private Integer code;

    private String message;

    ResponseCode() {}

    ResponseCode(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    ResponseCode(boolean flag, Integer code) {
        this.flag = flag;
        this.code = code;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
