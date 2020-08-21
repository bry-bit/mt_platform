package com.mt.pojo.frame;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 框架类汇总表子表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_ksummary_details {
    private String fd_id;//ID
    private String fd_parent_id;//关联主表ID
    private String fd_order_ids;//报价单子表ID
    private String fd_inventory_name;//设备名称
    private String fd_inventory_no;//物料编号
    private String fd_brand;//品牌
    private String fd_model;//型号
    private String fd_parameter;//参数
    private String fd_unit;//单位
    private String fd_price;//单价
    private String fd_cycle;//供货周期
    private String fd_methods;//付款方式
    private String fd_supplier_name;//供应商名称
    private String fd_supplier_code;//供应商编码
    private String fd_technical_para;//报价技术参数
    private String fd_offer;//报价
    private String fd_offer_time;//报价日期
    private String fd_validity_date;//报价有效期
    private String fd_remarks;//备注
    private String fd_add1;//备用字段
    private String fd_add2;//备用字段
    private String fd_add3;//备用字段
    private String fd_add4;//备用字段
}
