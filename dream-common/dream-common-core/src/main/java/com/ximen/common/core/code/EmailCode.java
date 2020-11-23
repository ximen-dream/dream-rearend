package com.ximen.common.core.code;

/**
 * @author zhishun.cai
 * @date 2020/8/11 10:37
 * @note
 */
public enum  EmailCode {
    DEBIT_NOTE(1,"注塑工艺服务平台账号激活失败及缴费通知","debitNote"),
    ACTIVE_NOTE(2,"账号开通及激活通知","activeNote"),
    USER_ACTIVE_NOTE(3,"用户已激活通知","activeNote"),
    MOULD_AUTHORIZE_NOTE(4,"模具被授权通知","activeNote"),
    ALARM_CONTINUE_NOTE(5,"连续报警通知","activeNote"),
    ALARM_TOTAL_NOTE(6,"累计报警通知","activeNote"),
    ALARM_STORE_NOTE(7,"存储报警通知","activeNote"),
    WARN_STORE_NOTE(8,"存储告警通知","activeNote"),
    EARLY_WARN_STORE_NOTE(9,"存储预警通知","activeNote"),
    RENEW_NOTE(10,"服务即将到期与续费通知","activeNote");

    private Integer type;

    private String subject;

    private String templateName;

    EmailCode(Integer type,String subject , String templateName){
        this.type = type;
        this.subject = subject;
        this.templateName = templateName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
