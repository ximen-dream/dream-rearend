package com.ximen.system.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ximen.common.core.dto.response.PageResultDTO;
import com.ximen.common.core.entity.QueryRequest;
import com.ximen.common.core.entity.system.SystemUser;
import com.ximen.common.core.exception.DreamException;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:23
 * @note
 */
public interface IUserService extends IService<SystemUser> {

    /**
     * 查找用户详细信息
     *
     * @param request request
     * @param user    用户对象，用于传递查询条件
     * @return IPage
     */
    IPage<SystemUser> findUserDetail(SystemUser user, QueryRequest request);

    /**
     * 新增用户
     *
     * @param user user
     */
    void createUser(SystemUser user);

    /**
     * 修改用户
     *
     * @param user user
     */
    void updateUser(SystemUser user);

    /**
     * 删除用户
     *
     * @param userIds 用户 id数组
     */
    void deleteUsers(String[] userIds);

    /**
     * 分页
     * @param pageNumber
     * @param pageSize
     * @param searchType
     * @param searchKey
     * @return
     */
    PageResultDTO<SystemUser> page(Integer pageNumber, Integer pageSize, Integer searchType, String searchKey);

    /**
     * 更新用户信息
     * @param user
     */
    void update(SystemUser user);

    /**
     * 添加用户
     * @param user
     */
    void add(SystemUser user);

    /**
     * 校验邮箱是否存在
     * @param email
     */
    void checkEmailIsExist(String email) throws DreamException;
}
