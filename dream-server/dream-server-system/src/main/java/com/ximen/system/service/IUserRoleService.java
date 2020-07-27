package com.ximen.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ximen.common.entity.system.UserRole;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:28
 * @note
 */
public interface IUserRoleService extends IService<UserRole> {

    void deleteUserRolesByRoleId(String[] roleIds);

    void deleteUserRolesByUserId(String[] userIds);
}