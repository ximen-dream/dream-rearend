package com.ximen.auth.controller;

import com.ximen.auth.entity.OauthClientDetails;
import com.ximen.auth.service.OauthClientDetailsService;
import com.ximen.common.core.entity.DreamResponse;
import com.ximen.common.core.entity.QueryRequest;
import com.ximen.common.core.exception.DreamException;
import com.ximen.common.core.utils.DreamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author zhishun.cai
 * @date 2020/7/29 9:49
 * @note
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("client")
public class OauthClientDetailsController {
    private final OauthClientDetailsService oauthClientDetailsService;

    @GetMapping("check/{clientId}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String clientId) {
        OauthClientDetails client = this.oauthClientDetailsService.findById(clientId);
        return client == null;
    }

    @GetMapping("secret/{clientId}")
    @PreAuthorize("hasAuthority('client:decrypt')")
    public DreamResponse getOriginClientSecret(@NotBlank(message = "{required}") @PathVariable String clientId) {
        OauthClientDetails client = this.oauthClientDetailsService.findById(clientId);
        String origin = client != null ? client.getOriginSecret() : StringUtils.EMPTY;
        return new DreamResponse().data(origin);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('client:view')")
    public DreamResponse oauthCliendetailsList(QueryRequest request, OauthClientDetails oAuthClientDetails) {
        Map<String, Object> dataTable = DreamUtil.getDataTable(this.oauthClientDetailsService.findOauthClientDetails(request, oAuthClientDetails));
        return new DreamResponse().data(dataTable);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('client:add')")
    public void addOauthCliendetails(@Valid OauthClientDetails oAuthClientDetails) throws DreamException {
        try {
            this.oauthClientDetailsService.createOauthClientDetails(oAuthClientDetails);
        } catch (Exception e) {
            String message = "新增客户端失败";
            log.error(message, e);
            throw new DreamException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('client:delete')")
    public void deleteOauthCliendetails(@NotBlank(message = "{required}") String clientIds) throws DreamException {
        try {
            this.oauthClientDetailsService.deleteOauthClientDetails(clientIds);
        } catch (Exception e) {
            String message = "删除客户端失败";
            log.error(message, e);
            throw new DreamException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('client:update')")
    public void updateOauthCliendetails(@Valid OauthClientDetails oAuthClientDetails) throws DreamException {
        try {
            this.oauthClientDetailsService.updateOauthClientDetails(oAuthClientDetails);
        } catch (Exception e) {
            String message = "修改客户端失败";
            log.error(message, e);
            throw new DreamException(message);
        }
    }
}
