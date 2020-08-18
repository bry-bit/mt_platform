package com.mt.pojo.standard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_inquiry_detailed {
    //子表ID
    private String fd_id;
    //关联主表ID
    private String fd_parent_id;
    //U8请购单子表ID
    private String fd_detailsid;
    //U8请购单主表ID
    private String fd_inquiryid;
    //存货名称
    private String fd_inventory_name;
    //存货编号
    private String fd_inventory_no;
    //规格型号
    private String fd_model;
    //主计量单位
    private String fd_unit;
    //可采购数量
    private String fd_purchase_avaqty;
    //税率
    private String fd_tax;
    //发货地址
    private String fd_ship_addr;
    //到货仓库
    private String fd_arrival_house;
    //开标时间
    private String fd_bid_opentime;
    //截标时间
    private String fd_bid_closetime;
    //供应商名称
    private String fd_supplier_name;
    //供应商名称
    private String fd_supplier_code;
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
    private String sum;
    private String suppliername;
    @Column(name = "fd_edit")
    private String fd_edit;
}
