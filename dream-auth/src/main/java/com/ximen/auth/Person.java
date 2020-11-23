package com.ximen.auth;

import com.ximen.auth.annotation.StartWithAnnotation;
import lombok.Data;

/**
 * @author zhishun.cai
 * @date 2020/7/30 16:11
 * @note
 */
@Data
public class Person {
    @StartWithAnnotation(start = "damon")
    private String name;
}
