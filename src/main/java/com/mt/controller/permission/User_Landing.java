package com.mt.controller.permission;

import com.mt.mapper.permission.User_LandingMapper;
import com.mt.pojo.permission.cy_user;
import com.mt.util.IDUtil;
import com.mt.util.JSONUtil;
import com.mt.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 供应商平台——用户登录
 */
@Controller
public class User_Landing {
    @Autowired
    private User_LandingMapper mapper;

    /**
     * 登陆供应商平台系统
     *
     * @param username 登陆名
     * @param password 登陆密码
     * @return
     */
    @RequestMapping("Register")
    @ResponseBody
    public String Register(@RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "password", required = false) String password) {
        try {
            System.out.println("username：" + username + "，password：" + password);

            cy_user user = new cy_user();
            user.setFd_login_name(username);
            user.setFd_password(password);

            List<cy_user> list = mapper.select(user);
            if (list.size() > 0) {
                return JSONUtil.toJson("0", list, "登陆成功！", "");
            } else {
                return JSONUtil.toJson("1", "", "登陆失败，用户名或密码错误！", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "登陆失败！", "");
        }
    }

    /**
     * 供应商平台菜单
     * @param name
     * @return
     */
    @RequestMapping("GeneratedMenu")
    @ResponseBody
    public Map<String, Object> GeneratedMenu(String name) {
        try {

            return MapUtil.toMap("0", "菜单获取成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return MapUtil.toMap("1", "菜单获取失败！", "");
        }
    }
}
