package com.mt.pojo.frame;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 框架类报价单
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_koffer {
    private String fd_id;//ID
    private String fd_inquiry_id;//询价单ID
    private String fd_inquiry_no;//报价单编号
    private String fd_applicant;//询价单制单人
    private String fd_creat_person;//创建人
    private String fd_creat_time;//创建时间
    private String fd_modificatio_person;//修改人
    private String fd_modificatio_time;//修改时间
    private String fd_suptype;//供应类型
    private String fd_remarks;//备注
    private String fd_quotation_tatus;//报价状态
    private String fd_add1;//备用字段
    private String fd_add2;//备用字段
    private String fd_add3;//备用字段
    private String fd_add4;//备用字段
}
