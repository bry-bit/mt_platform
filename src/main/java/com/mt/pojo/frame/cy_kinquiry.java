package com.mt.pojo.frame;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 供应商平台框架类询价单主表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_kinquiry {

    //ID
    private String fd_id;
    //创建人
    private String fd_creat_person;
    //创建时间
    private String fd_creat_time;
    //修改人
    private String fd_modificatio_person;
    //修改时间
    private String fd_modificatio_time;
    //文件名称
    private String fd_filename;
    //文件路径
    private String fd_url;
    //备注
    private String fd_remarks;
    //发布状态
    private String fd_status;
    //备用字段
    private String fd_add1;
    //备用字段
    private String fd_add2;

}
