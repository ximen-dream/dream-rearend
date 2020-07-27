package com.ximen.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ximen.common.entity.system.SystemUser;

/**
 * @author zhishun.cai
 * @date 2020/7/24 18:44
 * @note
 */
public interface UserMapper extends BaseMapper<SystemUser> {
    SystemUser findByName(String username);
}
