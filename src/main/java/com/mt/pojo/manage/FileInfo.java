package com.mt.pojo.manage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 导入文件信息
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileInfo {
    //ID
    private String fd_id;
    //主题ID
    private String fd_subject_id;
    //文件名称
    private String fd_file_name;
    //上传时间
    private String fd_time;
    //上传地址
    private String fd_url;
    //上传人
    private String fd_creat_person;
    //供应商名称
    private String suppliername;
    //供应商编码
    private String suppliercode;
    //供应商ID
    private String supplierid;
}
