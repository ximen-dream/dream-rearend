package com.ximen.system.system.controller;

import com.ximen.common.core.code.ResponseCode;
import com.ximen.common.core.dto.response.ResultDTO;
import com.ximen.common.core.entity.system.Role;
import com.ximen.common.core.exception.DreamException;
import com.ximen.system.system.dto.MenuInsertParamsDTO;
import com.ximen.system.system.service.IRoleMenuService;
import com.ximen.system.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRoleMenuService roleMenuService;

    @GetMapping("page")
    public ResultDTO page(){
        List<Role> roleList = this.roleService.page();
        return new ResultDTO(ResponseCode.SUCCESS,roleList);
    }

    @PostMapping
    public ResultDTO add(@RequestBody MenuInsertParamsDTO menuInsertParamsDTO){
        //1.添加角色
        Role role = new Role();
        role.setRemark(menuInsertParamsDTO.getRemark());
        role.setRoleName(menuInsertParamsDTO.getRoleName());
        role.setCreateTime(new Date());
        this.roleService.add(role);
        //添加权限关系
        this.roleMenuService.add(role.getRoleId(),menuInsertParamsDTO.getMenuIds());
        return new ResultDTO(ResponseCode.SUCCESS);
    }

    @DeleteMapping("{id}")
    public ResultDTO delete(@PathVariable("id") Long id) {
        //1.删除角色
        this.roleService.deleteById(id);
        //2.删除角色响应权限
        this.roleMenuService.deleteRoleMenusByRoleId(new String[] {id +""});
        return new ResultDTO(ResponseCode.SUCCESS);
    }

    @GetMapping("findRoleById/{id}")
    public ResultDTO findRoleById(@PathVariable("id") Long id){
        Role role = this.roleService.findRoleById(id);
        return new ResultDTO(ResponseCode.SUCCESS,role);
    }

    @PutMapping
    public ResultDTO update(@RequestBody MenuInsertParamsDTO menuInsertParamsDTO){
        //1.更新角色
        Role role = new Role();
        role.setRoleId(menuInsertParamsDTO.getRoleId());
        role.setRemark(menuInsertParamsDTO.getRemark());
        role.setRoleName(menuInsertParamsDTO.getRoleName());
        this.roleService.update(role);
        //2.删除旧权限
        this.roleMenuService.deleteRoleMenusByRoleId(new String[] {menuInsertParamsDTO.getRoleId() + ""} );
        //3.添加新权限
        this.roleMenuService.add(role.getRoleId(),menuInsertParamsDTO.getMenuIds());
        return new ResultDTO(ResponseCode.SUCCESS);
    }

    @GetMapping("checkRoleNameIsExist")
    public ResultDTO checkRoleNameIsExist(String roleName) throws DreamException {
        this.roleService.checkRoleNameIsExist(roleName);
        return new ResultDTO(ResponseCode.SUCCESS);
    }
}
