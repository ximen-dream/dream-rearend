package com.ximen.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ximen.common.core.entity.system.SystemUser;
import com.ximen.common.core.entity.system.UserDataPermission;

import java.util.List;

/**
 * @author zhishun.cai
 * @date 2020/7/24 18:44
 * @note
 */
public interface UserMapper extends BaseMapper<SystemUser> {
    /**
     * 获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    SystemUser findByName(String username);

    /**
     * 获取用户数据权限
     *
     * @param userId 用户id
     * @return 数据权限
     */
    List<UserDataPermission> findUserDataPermissions(Long userId);
}
