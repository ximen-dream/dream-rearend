package com.ximen.common.core.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhishun.cai
 * @date 2020/8/3 19:56
 * @note 分页结果DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResultDTO<T> {
    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页的数据列表
     */
    private List<T> rows;
}
