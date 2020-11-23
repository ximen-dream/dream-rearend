package com.ximen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ximen.auth.entity.OauthClientDetails;
import com.ximen.auth.mapper.OauthClientDetailsMapper;
import com.ximen.auth.service.OauthClientDetailsService;
import com.ximen.common.core.entity.QueryRequest;
import com.ximen.common.core.exception.DreamException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhishun.cai
 * @date 2020/7/29 9:21
 * @note
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisClientDetailsService redisClientDetailsService;

    @Override
    public IPage<OauthClientDetails> findOauthClientDetails(QueryRequest request, OauthClientDetails oauthClientDetails) {
        LambdaQueryWrapper<OauthClientDetails> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(oauthClientDetails.getClientId())) {
            queryWrapper.eq(OauthClientDetails::getClientId, oauthClientDetails.getClientId());
        }
        Page<OauthClientDetails> page = new Page<>(request.getPageNum(), request.getPageSize());
        IPage<OauthClientDetails> result = this.page(page, queryWrapper);

        List<OauthClientDetails> records = new ArrayList<>();
        result.getRecords().forEach(o -> {
            o.setOriginSecret(null);
            o.setClientSecret(null);
            records.add(o);
        });
        result.setRecords(records);
        return result;
    }

    @Override
    public OauthClientDetails findById(String clientId) {
        return this.baseMapper.selectById(clientId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOauthClientDetails(OauthClientDetails oauthClientDetails) throws DreamException {
        OauthClientDetails byId = this.findById(oauthClientDetails.getClientId());
        if (byId != null) {
            throw new DreamException("该Client已存在");
        }
        oauthClientDetails.setOriginSecret(oauthClientDetails.getClientSecret());
        oauthClientDetails.setClientSecret(passwordEncoder.encode(oauthClientDetails.getClientSecret()));
        boolean saved = this.save(oauthClientDetails);
        if (saved) {
            log.info("缓存Client -> {}", oauthClientDetails);
            this.redisClientDetailsService.loadClientByClientId(oauthClientDetails.getClientId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOauthClientDetails(OauthClientDetails oauthClientDetails) {
        String clientId = oauthClientDetails.getClientId();

        LambdaQueryWrapper<OauthClientDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OauthClientDetails::getClientId, oauthClientDetails.getClientId());

        oauthClientDetails.setClientId(null);
        oauthClientDetails.setClientSecret(null);
        boolean updated = this.update(oauthClientDetails, queryWrapper);
        if (updated) {
            log.info("更新Client -> {}", oauthClientDetails);
            this.redisClientDetailsService.removeRedisCache(clientId);
            this.redisClientDetailsService.loadClientByClientId(clientId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOauthClientDetails(String clientIds) {
        Object[] clientIdArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(clientIds, ",");
        LambdaQueryWrapper<OauthClientDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OauthClientDetails::getClientId, clientIdArray);
        boolean removed = this.remove(queryWrapper);
        if (removed) {
            log.info("删除ClientId为({})的Client", clientIds);
            Arrays.stream(clientIdArray).forEach(c -> this.redisClientDetailsService.removeRedisCache(String.valueOf(c)));
        }
    }
}

