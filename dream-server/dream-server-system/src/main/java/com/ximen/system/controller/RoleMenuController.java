package com.ximen.system.controller;

import com.ximen.common.core.code.ResponseCode;
import com.ximen.common.core.dto.response.ResultDTO;
import com.ximen.system.service.IMenuService;
import com.ximen.system.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("roleMenu")
public class RoleMenuController {

    @Autowired
    private IRoleMenuService roleMenuService;

    @GetMapping("findMenuIdsByRoleId/{id}")
    public ResultDTO findMenuIdsByRoleId(@PathVariable("id") String id) {
        List<Long> menuIds = this.roleMenuService.findMenuIdsByRoleId(id);
        return new ResultDTO(ResponseCode.SUCCESS,menuIds);
    }
}
