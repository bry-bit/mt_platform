package com.mt.controller.authorization;

import com.mt.mapper.authorization.Sporty_MenusMapper;
import com.mt.pojo.authorization.cy_menu;
import com.mt.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class Sporty_Menus {
    @Autowired
    private Sporty_MenusMapper mapper;

    @RequestMapping("findTree")
    @ResponseBody
    public Map<String, Object> findTree() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //查询所有菜单
            List<cy_menu> allMenu = mapper.Menu_List();
            //根节点
            List<cy_menu> rootMenu = new ArrayList<>();

            for (cy_menu nav : allMenu) {
                if (nav.getParentId().equals("0")) {
                    rootMenu.add(nav);
                }
            }
            //设置子菜单
            for (cy_menu nav : rootMenu) {
                List<cy_menu> childList = getChild(nav.getId(), allMenu);
                nav.setChildren(childList);
            }
            return MapUtil.toMap("0", "", rootMenu);
        } catch (Exception e) {
            e.printStackTrace();
            return MapUtil.toMap("1", "", new ArrayList<>());
        }
    }

    public List<cy_menu> getChild(String id, List<cy_menu> allMenu) {
        //子菜单
        List<cy_menu> childList = new ArrayList<cy_menu>();
        for (cy_menu nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (nav.getParentId().equals(id)) {
                childList.add(nav);
            }
        }
        //递归
        for (cy_menu nav : childList) {
            nav.setChildren(getChild(nav.getId(), allMenu));
        }

        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<cy_menu>();
        }
        return childList;
    }

}
