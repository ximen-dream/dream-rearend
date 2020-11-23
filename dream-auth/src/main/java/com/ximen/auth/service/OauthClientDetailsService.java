package com.ximen.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ximen.auth.entity.OauthClientDetails;
import com.ximen.common.core.entity.QueryRequest;
import com.ximen.common.core.exception.DreamException;

/**
 * @author zhishun.cai
 * @date 2020/7/29 9:12
 * @note
 */
public interface OauthClientDetailsService extends IService<OauthClientDetails> {
    /**
     * 查询（分页）
     *
     * @param request            QueryRequest
     * @param oauthClientDetails oauthClientDetails
     * @return IPage<OauthClientDetails>
     */
    IPage<OauthClientDetails> findOauthClientDetails(QueryRequest request, OauthClientDetails oauthClientDetails);

    /**
     * 根据主键查询
     *
     * @param clientId clientId
     * @return OauthClientDetails
     */
    OauthClientDetails findById(String clientId);

    /**
     * 新增
     *
     * @param oauthClientDetails oauthClientDetails
     * @throws DreamException FebsException
     */
    void createOauthClientDetails(OauthClientDetails oauthClientDetails) throws DreamException;

    /**
     * 修改
     *
     * @param oauthClientDetails oauthClientDetails
     */
    void updateOauthClientDetails(OauthClientDetails oauthClientDetails);

    /**
     * 删除
     *
     * @param clientIds clientIds
     */
    void deleteOauthClientDetails(String clientIds);
}
