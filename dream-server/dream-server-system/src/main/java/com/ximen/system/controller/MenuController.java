package com.ximen.system.controller;

import com.ximen.common.core.code.ResponseCode;
import com.ximen.common.core.dto.response.ResultDTO;
import com.ximen.common.core.entity.CurrentUser;
import com.ximen.common.core.entity.Tree;
import com.ximen.common.core.entity.router.VueRouter;
import com.ximen.common.core.entity.system.Menu;
import com.ximen.common.core.utils.DreamUtil;
import com.ximen.system.dto.MenuInsertParamsDTO;
import com.ximen.system.service.IMenuService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @GetMapping
    public ResultDTO list(){
        List<? extends Tree<?>> menuList = this.menuService.list();
        return new ResultDTO(ResponseCode.SUCCESS,menuList);
    }

    @PostMapping
    public ResultDTO add(@RequestBody Menu menu) {
        this.menuService.insert(menu);
        return new ResultDTO(ResponseCode.SUCCESS);
    }

    @PutMapping("{id}")
    public ResultDTO update(@RequestBody Menu menu, @PathVariable("id")Long id) {
        menu.setMenuId(id);
        this.menuService.update(menu);
        return new ResultDTO(ResponseCode.SUCCESS);
    }

    @DeleteMapping("{id}")
    public ResultDTO delete(@PathVariable("id")Long id) {
        this.menuService.deleteById(id);
        return new ResultDTO(ResponseCode.SUCCESS);
    }

    @GetMapping("routersAndPermissions")
    public ResultDTO routersAndPermissions() {
        Map map = new LinkedHashMap();
        CurrentUser currentUser = DreamUtil.getCurrentUser();
        //1.生成路由
        List<VueRouter<Menu>> vueRouters = this.menuService.generateRouters(currentUser.getUserId());
        map.put("routers",vueRouters);
        return new ResultDTO(ResponseCode.SUCCESS,map);
    }
}
