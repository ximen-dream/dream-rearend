package com.ximen.system.job.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhishun.cai
 */
@Slf4j
@Component
public class MyJob {

    public void test(String params) {
        log.info("我是带参数的test方法，正在被执行，参数为：{}", params);
    }

    public void test1() {
        log.info("我是不带参数的test1方法，正在被执行");
    }

    public void test2(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        log.info("我是带JSON参数的test2方法，正在被执行，参数为：{}", jsonObject);
    }
}
