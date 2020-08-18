package com.mt.pojo.permission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_roles {
    private String fd_id;//菜单ID
    private String fd_org_id;//组织ID
    private String fd_menu_name;//菜单名称
    private String fd_parent_id;//父菜单ID
    private String fd_menu_url;//菜单链接
    private String fd_creat_time;//创建时间
    private String fd_applicant;//创建人
    private String fd_modificatio_time;//修改时间
    private String fd_modificatio_person;//修改人

}
