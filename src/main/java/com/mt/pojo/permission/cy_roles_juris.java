package com.mt.pojo.permission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色权限表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_roles_juris {
    private String fd_id;//ID
    private String fd_roles_id;//角色ID
    private String fd_menu_id;//菜单ID
    private String fd_creat_time;//创建时间
    private String fd_applicant;//创建人
    private String fd_modificatio_time;//修改时间
    private String fd_modificatio_person;//修改人
}
