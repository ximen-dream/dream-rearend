package com.ximen.system.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.ximen.common.core.code.ResponseCode;
import com.ximen.common.core.dto.response.PageResultDTO;
import com.ximen.common.core.dto.response.ResultDTO;
import com.ximen.common.core.entity.DreamResponse;
import com.ximen.common.core.entity.QueryRequest;
import com.ximen.common.core.entity.system.SystemUser;
import com.ximen.common.core.exception.DreamException;
import com.ximen.common.core.utils.DreamUtil;
import com.ximen.system.system.service.IUserRoleService;
import com.ximen.system.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhishun.cai
 * @date 2020/7/25 9:29
 * @note
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:view')")
    public DreamResponse userList(QueryRequest queryRequest, SystemUser user) {
        Map<String, Object> dataTable = DreamUtil.getDataTable(userService.findUserDetail(user, queryRequest));
        return new DreamResponse().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user:add')")
    public void addUser(@Valid SystemUser user) throws DreamException {
        try {
            this.userService.createUser(user);
        } catch (Exception e) {
            String message = "新增用户失败";
            log.error(message, e);
            throw new DreamException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('user:update')")
    public void updateUser(@Valid SystemUser user) throws DreamException {
        try {
            this.userService.updateUser(user);
        } catch (Exception e) {
            String message = "修改用户失败";
            log.error(message, e);
            throw new DreamException(message);
        }
    }

    @DeleteMapping("/{userIds}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws DreamException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
        } catch (Exception e) {
            String message = "删除用户失败";
            log.error(message, e);
            throw new DreamException(message);
        }
    }

    @GetMapping("page/{pageNumber}/{pageSize}")
    public ResultDTO page(@PathVariable("pageNumber") Integer pageNumber,@PathVariable("pageSize") Integer pageSize, String searchKey,Integer searchType ){
        PageResultDTO<SystemUser> pageResultDTO = this.userService.page(pageNumber,pageSize,searchType,searchKey);
        return new ResultDTO(ResponseCode.SUCCESS,pageResultDTO);    }

    @PutMapping("update")
    public ResultDTO update(@RequestBody SystemUser user){
        this.userService.update(user);
        return new ResultDTO(ResponseCode.SUCCESS);
    }

    @PostMapping("add")
    public ResultDTO add(@RequestBody SystemUser user){
        //添加用户
        this.userService.add(user);
        //添加角色
        Set<Long> roleIds = Arrays.stream(user.getRoleId().split(",")).map(item -> Long.valueOf(item)).collect(Collectors.toSet());
        this.userRoleService.add(user.getUserId(),roleIds);
        return new ResultDTO(ResponseCode.SUCCESS);
    }

    @DeleteMapping("delete/{id}")
    public ResultDTO delete(@PathVariable("id") Long id){
        this.userService.removeById(id);
        return new ResultDTO(ResponseCode.SUCCESS);
    }

    @GetMapping("checkEmailIsExist")
    public ResultDTO checkEmailIsExist(@RequestParam String email) throws DreamException {
        this.userService.checkEmailIsExist(email);
        return new ResultDTO(ResponseCode.SUCCESS);
    }
}
