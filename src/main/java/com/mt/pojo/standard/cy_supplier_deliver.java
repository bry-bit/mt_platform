package com.mt.pojo.standard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 供应商发货主表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_supplier_deliver {
    private String fd_id;//ID
    private String fd_order_id;//报价单id
    private String fd_no;//U8请购单号
    private String fd_apply_no;//报价单单号
    private String fd_supplier_name;//供应商名称
    private String fd_supplier_code;//供应商编号
    private String fd_purorder_no;//U8采购订单号
    private String fd_creat_person;//创建人
    private String fd_creat_time;//创建时间
    private String fd_modificatio_person;//修改人
    private String fd_modificatio_time;//修改时间
    private String fd_add1;//备用字段
    private String fd_add2;//备用字段
    private String cPersonCode;
}
