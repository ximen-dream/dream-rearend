package com.ximen.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ximen.common.entity.system.Menu;

import java.util.List;

/**
 * @author zhishun.cai
 * @date 2020/7/24 18:44
 * @note
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findUserPermissions(String username);
}
