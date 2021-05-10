package com.ximen.system.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ximen.common.core.entity.system.Role;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:19
 * @note
 */
public interface RoleMapper extends BaseMapper<Role> {
    Role findRoleById(@Param("id") Long id);
}
