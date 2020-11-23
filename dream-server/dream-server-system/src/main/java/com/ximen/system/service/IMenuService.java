package com.ximen.system.service;

import com.ximen.common.core.entity.CurrentUser;
import com.ximen.common.core.entity.Tree;
import com.ximen.common.core.entity.router.VueRouter;
import com.ximen.common.core.entity.system.Menu;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface IMenuService {
    List<? extends Tree<?>> list();

    /**
     * 添加
     * @param menu
     */
    void insert(Menu menu);

    /**
     * 根据ID删除
     */
    void deleteById(Long id);

    /**
     * 更新
     * @param menu
     */
    void update(Menu menu);

    /**
     * 生成动态路由
     * @param userId
     */
    List<VueRouter<Menu>> generateRouters(Long userId);
}
