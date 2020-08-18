package com.mt.pojo.permission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_user {
    private String fd_id;//ID
    private String fd_login_name;//登录名
    private String fd_user_name;//用户名称
    private String fd_password;//密码
    private String fd_iphone;//联系方式
    private String fd_creat_time;//创建时间
    private String fd_applicant;//创建人
    private String fd_modificatio_time;//修改时间
    private String fd_modificatio_person;//修改人
    private String fd_is_avaible;//是否禁用
    private String limits_state;//权限类型
    private String limits_type;//权限状态
}
