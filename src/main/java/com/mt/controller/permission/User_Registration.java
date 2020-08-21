package com.mt.controller.permission;

import com.alibaba.fastjson.JSONObject;
import com.mt.mapper.permission.User_RegistrationMapper;
import com.mt.pojo.permission.cy_user;
import com.mt.util.IDUtil;
import com.mt.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.soap.Addressing;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * 供应商平台——用户注册
 */
@Controller
public class User_Registration {
    @Autowired
    private User_RegistrationMapper mapper;

    /**
     * 供应商注册
     *
     * @param data 注册时传来的数据
     * @return
     */
    @RequestMapping("Enroll")
    @ResponseBody
    public String Enroll(@RequestBody String data) {
        try {
            cy_user cyUser = JSONObject.parseObject(data, cy_user.class);

            //判断用户是否存在
            List<cy_user> userList = mapper.IsOrNotUser(cyUser.getFd_user_name());
            //若存在则返回“已存在”
            if (!userList.isEmpty()) {
                return JSONUtil.toJson("2", "", "注册失败！已存在该用户！", "");
            }

            //判断登陆名是否存在
            List<cy_user> loginList = mapper.IsOrNotLogin(cyUser.getFd_login_name());
            if (!loginList.isEmpty()) {
                return JSONUtil.toJson("3", "", "注册失败！已存在该登陆名称！", "");
            }

            if (userList.isEmpty() && loginList.isEmpty()) {
                cyUser.setFd_id(IDUtil.getUUID());
                //获取服务器时间
                cyUser.setFd_creat_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                //创建人为创建的用户
                cyUser.setFd_applicant(cyUser.getFd_user_name());
                //权限状态：“1”超级管理员；“2”管理员；“3”采购员；“4”技术员；“5”供应商
                cyUser.setLimits_type("供应商");
                cyUser.setLimits_state("5");
                //是否禁用：“Y”禁用；“N”不禁用
                cyUser.setFd_is_avaible("N");
                mapper.insert(cyUser);
            }
            return JSONUtil.toJson("0", "", "注册成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "注册失败！", "");
        }
    }
}
