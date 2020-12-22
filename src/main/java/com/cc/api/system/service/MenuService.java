package com.cc.api.system.service;

import com.cc.api.common.pojo.system.Menu;
import com.cc.api.system.vo.MenuNodeVO;
import com.cc.api.system.vo.MenuVO;

import java.util.List;

public interface MenuService {
    /**
     * 获取菜单树
     * @return
     */
    List<MenuNodeVO> findMenuTree();

    /**
     * 添加菜单
     * @param menuVO
     */
    Menu add(MenuVO menuVO);

    /**
     * 删除节点
     * @param id
     */
    void delete(Long id);

    /**
     * 编辑节点
     * @param id
     * @return
     */
    MenuVO edit(Long id);

    /**
     * 更新节点
     * @param id
     */
    void update(Long id, MenuVO menuVO);

    /**
     * 所有展开菜单的ID
     * @return
     */
    List<Long> findOpenIds();


    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> findAll();

}
