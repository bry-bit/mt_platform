package com.mt.pojo.permission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 组织表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_org {
    private String fd_id;//ID
    private String fd_org_name;//组织名称
    private String fd_org_simname;//组织简称
    private String fd_creat_time;//创建时间
    private String fd_applicant;//创建人
    private String fd_modificatio_time;//修改时间
    private String fd_modificatio_person;//修改人

}
