package com.ximen.common.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.Set;

/**
 * @author zhishun.cai
 * @date 2020/7/29 20:28
 * @note
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUser {
    private static long serialVersionUID = 761748087824726463L;

    @JsonIgnore
    private String password;
    private String username;
    @JsonIgnore
    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Long userId;
    private String avatar;
    private String email;
    private String mobile;
    private String sex;
    private Long deptId;
    private String deptName;
    private String roleId;
    private String roleName;
    @JsonIgnore
    private Date lastLoginTime;
    private String description;
    private String status;
    private String deptIds;
}
