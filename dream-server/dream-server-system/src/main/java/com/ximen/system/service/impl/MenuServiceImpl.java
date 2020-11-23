package com.ximen.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ximen.common.core.entity.CurrentUser;
import com.ximen.common.core.entity.MenuTree;
import com.ximen.common.core.entity.Tree;
import com.ximen.common.core.entity.router.RouterMeta;
import com.ximen.common.core.entity.router.VueRouter;
import com.ximen.common.core.entity.system.Menu;
import com.ximen.common.core.entity.system.RoleMenu;
import com.ximen.common.core.entity.system.UserRole;
import com.ximen.common.core.utils.TreeUtil;
import com.ximen.system.mapper.MenuMapper;
import com.ximen.system.mapper.RoleMenuMapper;
import com.ximen.system.service.IMenuService;
import com.ximen.system.service.IRoleMenuService;
import com.ximen.system.service.IUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public List<? extends Tree<?>> list() {
        List<Menu> menus = this.menuMapper.selectList(null);

        List<MenuTree> trees = new ArrayList<>();
        buildTrees(trees, menus);
        List<? extends Tree<?>> menuTree = TreeUtil.build(trees);
        return menuTree;
    }

    /**
     * 添加
     * @param menu
     */
    @Override
    public void insert(Menu menu) {
        this.menuMapper.insert(menu);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        //1. 删除自身和子权限
        this.menuMapper.delete(new LambdaQueryWrapper<Menu>().eq(Menu::getMenuId,id).or().eq(Menu::getParentId,id));
        //2.删除角色权限关系
        this.roleMenuService.deleteRoleMenusByMenuId(new String[] {id + ""});
    }

    @Override
    public void update(Menu menu) {
        this.menuMapper.updateById(menu);
    }

    /**
     * 生成动态路由
     * @param userId
     */
    @Override
    public List<VueRouter<Menu>> generateRouters(Long userId) {
        //1.获取用户角色集合
        List<UserRole> roleMenus =  userRoleService.findByUserId(userId);
        Set<Long> menuIds = new LinkedHashSet<>();
        roleMenus.forEach(roleMenu -> {
            menuIds.addAll(this.roleMenuService.findMenuIdsByRoleId(roleMenu.getRoleId() + ""));
        });
        if(CollectionUtils.isEmpty(menuIds)) return new ArrayList<VueRouter<Menu>>();
        List<Menu> menuList = this.menuMapper.selectBatchIds(menuIds);
        List<VueRouter<Menu>> routes = new ArrayList<>();
        menuList.forEach(menu -> {
            VueRouter<Menu> route = new VueRouter<>();
            route.setId(menu.getMenuId().toString());
            route.setParentId(menu.getParentId().toString());
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getMenuName());
            route.setMeta(new RouterMeta(menu.getMenuName(), menu.getIcon(), true));
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }


    private void buildTrees(List<MenuTree> trees, List<Menu> menus) {
        menus.forEach(menu -> {
            MenuTree tree = new MenuTree();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setLabel(menu.getMenuName());
            tree.setComponent(menu.getComponent());
            tree.setIcon(menu.getIcon());
            tree.setOrderNum(menu.getOrderNum());
            tree.setPath(menu.getPath());
            tree.setType(menu.getType());
            tree.setPerms(menu.getPerms());
            trees.add(tree);
        });
    }
}
