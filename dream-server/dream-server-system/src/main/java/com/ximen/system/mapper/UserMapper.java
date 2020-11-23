package com.ximen.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ximen.common.core.entity.system.SystemUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:19
 * @note
 */
public interface UserMapper extends BaseMapper<SystemUser> {

    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @return Ipage
     */
    IPage<SystemUser> findUserDetailPage(Page page, @Param("user") SystemUser user);

    IPage<SystemUser> page(@Param("pageInfo") Page<SystemUser> pageInfo, @Param("searchType") Integer searchType, @Param("searchKey") String searchKey);

    /**
     * 更新用户
     * @param user
     */
    void update(@Param("user") SystemUser user);
}
