package com.mt.pojo.standard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;

/**
 * 标准流程报价单子表实体表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_order_detailed {
    @Column(name = "fd_id")
    private String fd_id;//子表ID
    @Column(name = "fd_parent_id")
    private String fd_parent_id;//关联主表ID
    @Column(name = "fd_inquiry_ids")
    private String fd_inquiry_ids;//询价单子表ID
    @Column(name = "fd_order_person")
    private String fd_order_person;//采购员
    @Column(name = "fd_inventory_name")
    private String fd_inventory_name;//存货名称
    @Column(name = "fd_inventory_no")
    private String fd_inventory_no;//存货编号
    @Column(name = "fd_model")
    private String fd_model;//规格型号
    @Column(name = "fd_unit")
    private String fd_unit;//主计量单位
    @Column(name = "fd_purchase_avaqty")
    private String fd_purchase_avaqty;//可采购数量
    @Column(name = "fd_tax")
    private String fd_tax;//税率
    @Column(name = "fd_offer")
    private String fd_offer;//报价
    @Column(name = "fd_offer_time")
    private String fd_offer_time;//报价日期
    @Column(name = "fd_ship_addr")
    private String fd_ship_addr;//发货地址
    @Column(name = "fd_arrival_house")
    private String fd_arrival_house;//到货仓库
    @Column(name = "fd_bid_opentime")
    private String fd_bid_opentime;//开标时间
    @Column(name = "fd_bid_closetime")
    private String fd_bid_closetime;//截标时间
    @Column(name = "fd_supplier_name")
    private String fd_supplier_name;//供应商名称
    @Column(name = "fd_supplier_code")
    private String fd_supplier_code;//供应商编号
    @Column(name = "fd_shipment_qty")
    private String fd_shipment_qty;//发货数量
    @Column(name = "fd_storage_qty")
    private String fd_storage_qty;//入库数量
    @Column(name = "fd_arrival_certificate")
    private String fd_arrival_certificate;//到货凭证
    @Column(name = "fd_invoice_amount")
    private String fd_invoice_amount;//发票金额（含税）
    @Column(name = "fd_payment_amount")
    private String fd_payment_amount;//付款金额（含税）
    @Column(name = "fd_qualified")
    private String fd_qualified;//技术员校验
    @Column(name = "fd_attachment")
    private String fd_attachment;//附件上传
    @Column(name = "fd_return")
    private String fd_return;//退回操作
    @Column(name = "fd_return_reason")
    private String fd_return_reason;//退回理由
    @Column(name = "fd_remarks")
    private String fd_remarks;//备注
    @Column(name = "fd_quotation_tatus")
    private String fd_quotation_tatus;//报价状态
    @Column(name = "fd_modificatio_person")
    private String fd_modificatio_person;//修改人
    @Column(name = "fd_modificatio_time")
    private String fd_modificatio_time;//修改时间
    @Column(name = "fd_add1")
    private String fd_add1;//备用字段
    @Column(name = "fd_add2")
    private String fd_add2;//备用字段
    private String cComUnitName;
}
