package com.ximen.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ximen.common.core.entity.CurrentUser;
import com.ximen.common.core.entity.system.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户ID获取权限集合
     * @param userId
     * @return
     */
    List<Menu> findMenusByUserId(@Param("userId") Long userId);
}
