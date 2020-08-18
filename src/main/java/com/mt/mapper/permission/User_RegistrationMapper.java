package com.mt.mapper.permission;

import com.mt.pojo.permission.cy_user;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User_RegistrationMapper {
    //用户注册插入到用户表中
    void insert(cy_user user);

    //判断该用户是否存在
    List<cy_user> IsOrNotUser(String fd_user_name);

    //判断该登陆名是否存在
    List<cy_user> IsOrNotLogin(String fd_login_name);
}
