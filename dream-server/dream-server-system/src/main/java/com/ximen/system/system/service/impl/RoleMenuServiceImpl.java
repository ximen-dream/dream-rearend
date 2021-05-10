package com.ximen.system.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ximen.common.core.entity.system.RoleMenu;
import com.ximen.system.system.mapper.RoleMenuMapper;
import com.ximen.system.system.service.IRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MrBird
 */
@Service("roleMenuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByRoleId(String[] roleIds) {
        List<String> list = Arrays.asList(roleIds);
        if(CollectionUtils.isEmpty(list)) {
            return;
        }
        baseMapper.delete(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getRoleId, list));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByMenuId(String[] menuIds) {
        List<String> list = Arrays.asList(menuIds);
        baseMapper.delete(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getMenuId, list));
    }

    @Override
    public List<RoleMenu> getRoleMenusByRoleId(String roleId) {
        return baseMapper.selectList(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
    }

    @Override
    public void add(Long roleId, List<Long> menuIds) {
        menuIds.forEach(menuId -> this.baseMapper.insert(new RoleMenu(roleId,menuId)));
    }

    @Override
    public List<Long> findMenuIdsByRoleId(String id) {
        List<RoleMenu> roleMenus = baseMapper.selectList(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, id));
        return roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
    }
}
