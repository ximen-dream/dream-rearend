package com.ximen.common.entity;

import java.util.HashMap;

/**
 * @author zhishun.cai
 * @date 2020/7/19 11:47
 * @note
 */
public class DreamResponse extends HashMap<String, Object> {
    private static final long serialVersionUID = -8713837118340960775L;

    public DreamResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public DreamResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public DreamResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }
}
