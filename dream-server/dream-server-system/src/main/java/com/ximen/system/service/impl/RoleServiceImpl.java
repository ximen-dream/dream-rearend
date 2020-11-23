package com.ximen.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ximen.common.core.code.ResponseCode;
import com.ximen.common.core.entity.system.Role;
import com.ximen.common.core.exception.DreamException;
import com.ximen.system.dto.MenuInsertParamsDTO;
import com.ximen.system.mapper.RoleMapper;
import com.ximen.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 列表
     * @return
     */
    @Override
    public List<Role> page() {
        return this.roleMapper.selectList(null);
    }

    @Override
    public void add(Role role) {
        this.roleMapper.insert(role);
    }

    @Override
    public void deleteById(Long id) {
        this.roleMapper.deleteById(id);
    }

    @Override
    public Role findRoleById(Long id) {
        return this.roleMapper.findRoleById(id);
    }

    @Override
    public void update(Role role) {
        this.roleMapper.updateById(role);
    }

    @Override
    public void checkRoleNameIsExist(String roleName) throws DreamException {
        List<Role> roles = this.roleMapper.selectList(new LambdaQueryWrapper<Role>().like(Role::getRoleName, roleName));
        if(!CollectionUtils.isEmpty(roles)) {
            throw new DreamException(ResponseCode.ERROR_EXIST_ROLENAME);
        }
    }
}
