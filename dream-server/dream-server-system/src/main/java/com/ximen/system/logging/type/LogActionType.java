package com.ximen.system.logging.type;

/**
 * @author zhishun.cai
 * @date 2021/6/17 16:10
 * @note 日志类型
 */

public enum LogActionType {
    /**
     * 增删改查
     */
    ADD("新增"),
    SELECT("查询"),
    UPDATE("更新"),
    DELETE("删除");
    private String value;

    LogActionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

