package com.ximen.system.service;

import com.ximen.common.core.entity.system.Role;
import com.ximen.common.core.exception.DreamException;
import com.ximen.system.dto.MenuInsertParamsDTO;

import java.util.List;

public interface IRoleService {

    /**
     * 列表
     * @return
     */
    List<Role> page();

    /**
     * 添加
     * @param role
     */
    void add(Role role);

    void deleteById(Long id);

    Role findRoleById(Long id);

    void update(Role role);

    void checkRoleNameIsExist(String roleName) throws DreamException;
}
