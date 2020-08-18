package com.mt.pojo.standard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 报价单主表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_order {
    private String fd_id;//ID
    private String fd_no;//询价单号
    private String fd_applicant;//询价单制单人
    private String fd_creat_person;//创建人
    private String fd_creat_time;//创建时间
    private String fd_modificatio_person;//修改人
    private String fd_modificatio_time;//修改时间
    private String fd_apply_no;//U8请购单单号
    private String fd_apply_time;//U8请购单日期
    private String fd_apply_department;//U8请购单部门
    private String fd_apply_bustype;//U8请购单业务类型
    private String fd_apply_purtype;//U8请购单采购类型
    private String fd_purchase_avaqty;//可采购数量
    private String fd_bid_opentime;//开标时间
    private String fd_bid_closetime;//截标时间
    private String fd_supplier_name;//供应商名称
    private String fd_supplier_code;//供应商编号
    private String fd_purorder_no;//U8采购订单号
    private String fd_quotation_tatus;//报价状态
    private String fd_remarks;//备注
    private String fd_add1;//备用字段
    private String fd_add2;//备用字段
    private String fd_add3;//备用字段
    private String fd_add4;//备用字段
    private String fd_inquiryid;//询价单ID
}
