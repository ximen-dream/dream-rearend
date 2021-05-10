package com.ximen.auth.manager;

import com.ximen.auth.mapper.MenuMapper;
import com.ximen.auth.mapper.UserMapper;
import com.ximen.auth.mapper.UserRoleMapper;
import com.ximen.common.core.entity.constant.DreamConstant;
import com.ximen.common.core.entity.system.Menu;
import com.ximen.common.core.entity.system.SystemUser;
import com.ximen.common.core.entity.system.UserDataPermission;
import com.ximen.common.core.entity.system.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhishun.cai
 * @date 2020/7/24 18:47
 * @note 用户业务逻辑
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserManager {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户
     */
    public SystemUser findByName(String username) {
        SystemUser user = userMapper.findByName(username);
        if (user != null) {
            List<UserDataPermission> permissions = userMapper.findUserDataPermissions(user.getUserId());
            String deptIds = permissions.stream().map(p -> String.valueOf(p.getDeptId())).collect(Collectors.joining(","));
            user.setDeptIds(deptIds);
        }
        return user;
    }

    /**
     * 通过用户名查询用户权限串
     *
     * @param username 用户名
     * @return 权限
     */
    public String findUserPermissions(String username) {
        List<Menu> userPermissions = menuMapper.findUserPermissions(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.joining(","));
    }

    /**
     * 注册用户
     *
     * @param username username
     * @param password password
     * @return SystemUser SystemUser
     */
    @Transactional(rollbackFor = Exception.class)
    public SystemUser registUser(String username, String password,String avatar) {
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(username);
        systemUser.setPassword(password);
        systemUser.setCreateTime(new Date());
        systemUser.setStatus(SystemUser.STATUS_VALID);
        systemUser.setSex(SystemUser.SEX_UNKNOW);
        System.out.println("avatar: " + avatar);
        systemUser.setAvatar(avatar);
        systemUser.setDescription("注册用户");
        this.userMapper.insert(systemUser);

        UserRole userRole = new UserRole();
        userRole.setUserId(systemUser.getUserId());
        // 注册用户角色 ID
        userRole.setRoleId(DreamConstant.REGISTER_ROLE_ID);
        this.userRoleMapper.insert(userRole);
        return systemUser;
    }

    public void updateUserAvatar(Long userId, String avatar) {
        userMapper.updateUserAvatar(userId,avatar);
    }
}
