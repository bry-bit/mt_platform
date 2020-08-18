package com.mt.pojo.frame;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 供应商平台框架类询价单子表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_kinquiry_details {
    //ID
    private String fd_id;
    //关联主表ID
    private String fd_parent_id;
    //序号
    private String fd_order;
    //设备名称
    private String fd_inventory_name;
    //物料编号
    private String fd_inventory_no;
    //品牌
    private String fd_brand;
    //型号
    private String fd_model;
    //参数
    private String fd_parameter;
    //单位
    private String fd_unit;
    //单价
    private String fd_price;
    //供货周期
    private String fd_cycle;
    //付款方式
    private String fd_methods;
    //开标时间
    private String fd_bid_opentime;
    //截标时间
    private String fd_bid_closetime;
    //供应商名称
    private String fd_supplier_name;
    //供应商名称
    private String fd_supplier_code;
    //报价技术参数
    private String fd_technical_para;
    //报价有效期
    private String fd_validity;
    //备注
    private String fd_remarks;
    //发布状态
    private String fd_status;
    //备用字段
    private String fd_add1;
    //备用字段
    private String fd_add2;
    //备用字段
    private String fd_add3;
    //备用字段
    private String fd_add4;
}
