package com.ximen.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ximen.common.core.entity.system.RoleMenu;
import com.ximen.common.core.entity.system.UserRole;

import java.util.List;
import java.util.Set;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:28
 * @note
 */
public interface IUserRoleService extends IService<UserRole> {

    void deleteUserRolesByRoleId(String[] roleIds);

    void deleteUserRolesByUserId(String[] userIds);

    /**
     * 更新用户角色关系
     * @param userId
     * @param roleIds
     */
    void update(Long userId, Set<Long> roleIds);

    public void add(Long userId, Set<Long> roleIds);

    /**
     * 根据用户ID查询角色集合
     * @param userId
     * @return
     */
    List<UserRole> findByUserId(Long userId);
}