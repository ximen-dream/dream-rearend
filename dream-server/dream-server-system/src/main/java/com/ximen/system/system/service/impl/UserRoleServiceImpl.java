package com.ximen.system.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ximen.common.core.entity.system.UserRole;
import com.ximen.system.system.mapper.UserRoleMapper;
import com.ximen.system.system.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:29
 * @note
 */
@Service("userRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    @Transactional
    public void deleteUserRolesByRoleId(String[] roleIds) {
        Arrays.stream(roleIds).forEach(id -> baseMapper.deleteByRoleId(Long.valueOf(id)));
    }

    @Override
    @Transactional
    public void deleteUserRolesByUserId(String[] userIds) {
        Arrays.stream(userIds).forEach(id -> baseMapper.deleteByUserId(Long.valueOf(id)));
    }

    /**
     * 更新用户角色关系
     * @param userId
     * @param roleIds
     */
    @Override
    public void update(Long userId, Set<Long> roleIds) {
        //1.根据用户ID删除旧用户角色关系
        this.deleteUserRolesByUserId(new String[]{userId+""});
        //2.添加用户角色关系
        roleIds.stream().forEach(roleId -> baseMapper.insert(new UserRole(userId,roleId)));
    }

    @Override
    public void add(Long userId, Set<Long> roleIds){
        roleIds.stream().forEach(roleId -> baseMapper.insert(new UserRole(userId,roleId)));
    }

    /**
     * 根据用户ID查询角色集合
     * @param userId
     * @return
     */
    @Override
    public List<UserRole> findByUserId(Long userId) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,userId));
    }
}
