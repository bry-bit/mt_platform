package com.mt.mapper.permission;

import com.mt.pojo.permission.cy_user;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User_LandingMapper {
    //登录时进行查询是否存在该用户
    List<cy_user> select(cy_user user);
}
