package com.mt.pojo.manage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 管理类主题表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_manage_subject {
    private String fd_id;//ID
    private String fd_subject;//文件主题
    private String fd_creat_time;//创建时间
    private String fd_creat_person;//创建人
    private String fd_modificatio_person;//修改人
    private String fd_modificatio_time;//修改时间
}
