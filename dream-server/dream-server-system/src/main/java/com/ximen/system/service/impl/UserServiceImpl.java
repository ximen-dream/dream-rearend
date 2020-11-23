package com.ximen.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ximen.common.core.code.ResponseCode;
import com.ximen.common.core.dto.response.PageResultDTO;
import com.ximen.common.core.entity.QueryRequest;
import com.ximen.common.core.entity.system.SystemUser;
import com.ximen.common.core.entity.system.UserRole;
import com.ximen.common.core.exception.DreamException;
import com.ximen.system.mapper.UserMapper;
import com.ximen.system.service.IRoleService;
import com.ximen.system.service.IUserRoleService;
import com.ximen.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:26
 * @note
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, SystemUser> implements IUserService {

    final String initPwd = "123";

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<SystemUser> findUserDetail(SystemUser user, QueryRequest request) {
        Page<SystemUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findUserDetailPage(page, user);
    }

    @Override
    @Transactional
    public void createUser(SystemUser user) {
        // 创建用户
        user.setCreateTime(new Date());
        user.setAvatar(SystemUser.DEFAULT_AVATAR);
        user.setPassword(passwordEncoder.encode(SystemUser.DEFAULT_PASSWORD));
        save(user);
        // 保存用户角色
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);
    }

    @Override
    @Transactional
    public void updateUser(SystemUser user) {
        // 更新用户
        user.setPassword(null);
        user.setUsername(null);
        user.setCreateTime(null);
        user.setModifyTime(new Date());
        updateById(user);

        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getUserId()));
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);
    }

    @Override
    @Transactional
    public void deleteUsers(String[] userIds) {
        List<String> list = Arrays.asList(userIds);
        removeByIds(list);
        // 删除用户角色
        this.userRoleService.deleteUserRolesByUserId(userIds);
    }

    /**
     * 分页
     * @param pageNumber
     * @param pageSize
     * @param searchType
     * @param searchKey
     * @return
     */
    @Override
    public PageResultDTO<SystemUser> page(Integer pageNumber, Integer pageSize, Integer searchType, String searchKey) {
        Page<SystemUser> pageInfo = new Page<SystemUser>(pageNumber,pageSize);
        IPage<SystemUser> pageRes = this.userMapper.page(pageInfo,searchType,searchKey);
        return new PageResultDTO<SystemUser>(pageRes.getTotal(),pageRes.getRecords());
    }

    /**
     * 更新用户信息
     * @param user
     */
    @Override
    public void update(SystemUser user) {
        //1.更新用户基本信息
        this.userMapper.update(user);
        //2.更新用户角色信息
        String roleId = user.getRoleId();
        if(StringUtils.isNotBlank(roleId)){
            Set<Long> roleIds = Arrays.stream(roleId.split(",")).map(item -> Long.valueOf(item)).collect(Collectors.toSet());
            this.userRoleService.update(user.getUserId(), roleIds);
        }

    }

    /**
     * 添加用户
     * @param user
     */
    @Override
    public void add(SystemUser user) {
        user.setCreateTime(new Date());
        user.setStatus("1");
        user.setPassword(passwordEncoder.encode(initPwd));
        this.userMapper.insert(user);
    }


    /**
     * 校验邮箱是否存在
     * @param email
     */
    @Override
    public void checkEmailIsExist(String email) throws DreamException {
        List<SystemUser> systemUsers = this.userMapper.selectList(new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getEmail, email));
        if(!CollectionUtils.isEmpty(systemUsers)) {
            throw new DreamException(ResponseCode.ERROR_EXIST_EMAIL);
        }
    }

    private void setUserRoles(SystemUser user, String[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(Long.valueOf(roleId));
            userRoleService.save(ur);
        });
    }
}
