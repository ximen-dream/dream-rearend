package com.ximen.auth.service.impl;

import com.ximen.common.core.entity.DreamAuthUser;
import com.ximen.auth.manager.UserManager;
import com.ximen.common.core.entity.constant.ParamsConstant;
import com.ximen.common.core.entity.constant.SocialConstant;
import com.ximen.common.core.entity.system.SystemUser;
import com.ximen.common.core.utils.DreamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhishun.cai
 * @date 2020/7/19 11:44
 * @note
 */
@Service
public class DreamUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest httpServletRequest = DreamUtil.getHttpServletRequest();
        //根据用户名查询用户
        SystemUser systemUser = userManager.findByName(username);
        if (systemUser != null) {
            //获取用户权限
            String permissions = userManager.findUserPermissions(systemUser.getUsername());
            boolean notLocked = false;
            if (StringUtils.equals(SystemUser.STATUS_VALID, systemUser.getStatus())) {
                notLocked = true;
            }
            String password = systemUser.getPassword();
            String loginType = (String) httpServletRequest.getAttribute(ParamsConstant.LOGIN_TYPE);
            if (StringUtils.equals(loginType, SocialConstant.SOCIAL_LOGIN)) {
                password = passwordEncoder.encode(SocialConstant.SOCIAL_LOGIN_PASSWORD);
            }

            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.NO_AUTHORITIES;
            if (StringUtils.isNotBlank(permissions)) {
                grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
            }
            DreamAuthUser authUser = new DreamAuthUser(systemUser.getUsername(), password, true, true, true, notLocked,
                    grantedAuthorities);

            BeanUtils.copyProperties(systemUser, authUser);
            return authUser;
        } else {
            throw new UsernameNotFoundException("");
        }
    }
}
