package com.mt.pojo.standard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 供应商发货子表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_supplier_deliver_details {
    private String fd_id;//ID
    private String fd_parent_id;//关联主表ID
    private String fd_order_ids;//报价单子表id
    private String fd_inventory_no;//存货编号
    private String fd_inventory_name;//存货名称
    private String fd_model;//规格型号
    private String fd_unit;//主计量单位
    private String fd_purchase_avaqty;//可采购数量
    private String fd_tax;//税率
    private String fd_ship_addr;//发货地址
    private String fd_arrival_house;//到货仓库
    private String fd_shipment_qty;//发货数量
    private String fd_storage_qty;//入库数量
    private String fd_billing_status;//开票状态
    private String fd_invoice_amount;//发票金额（含税）
    private String fd_payment_status;//付款状态
    private String fd_payment_amount;//付款金额（含税）
    private String fd_arrival_certificate;//到货凭证
    private String fd_remarks;//备注
    private String fd_arrival_button;//到货确认按钮
    private String fd_add1;//备用字段
    private String fd_add2;//备用字段
    private String fd_add3;//备用字段
    private String fd_add4;//备用字段
}
