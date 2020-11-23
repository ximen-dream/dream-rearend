package com.ximen.common.core.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:24
 * @note
 */
@Data
@ToString
public class QueryRequest implements Serializable {
    private static final long serialVersionUID = -4869594085374385813L;
    /**
     * 当前页面数据量
     */
    private int pageSize = 10;
    /**
     * 当前页码
     */
    private int pageNum = 1;
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序规则，asc升序，desc降序
     */
    private String order;
}
