package com.mt.pojo.manage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 下载记录
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DownLoad {

    //ID
    private String fd_id;
    //文件ID
    private String fd_fileid;
    //下载时间
    private String fd_download_date;
    //下载人
    private String fd_download_person;
    //下载次数
    private String fd_download_number;

}
