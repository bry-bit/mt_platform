package com.mt.pojo.standard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 标准流程报价单子表实体表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_order_detailed {
    private String fd_id;//子表ID
    private String fd_parent_id;//关联主表ID
    private String fd_inquiry_ids;//询价单子表ID
    private String fd_order_person;//采购员
    private String fd_inventory_name;//存货名称
    private String fd_inventory_no;//存货编号
    private String fd_model;//规格型号
    private String fd_unit;//主计量单位
    private String fd_purchase_avaqty;//可采购数量
    private String fd_tax;//税率
    private String fd_offer;//报价
    private String fd_offer_time;//报价日期
    private String fd_ship_addr;//发货地址
    private String fd_arrival_house;//到货仓库
    private String fd_bid_opentime;//开标时间
    private String fd_bid_closetime;//截标时间
    private String fd_supplier_name;//供应商名称
    private String fd_supplier_code;//供应商编号
    private String fd_shipment_qty;//发货数量
    private String fd_storage_qty;//入库数量
    private String fd_arrival_certificate;//到货凭证
    private String fd_invoice_amount;//发票金额（含税）
    private String fd_payment_amount;//付款金额（含税）
    private String fd_qualified;//技术员校验
    private String fd_attachment;//附件上传
    private String fd_return;//退回操作
    private String fd_return_reason;//退回理由
    private String fd_remarks;//备注
    private String fd_quotation_tatus;//报价状态
    private String fd_modificatio_person;//修改人
    private String fd_modificatio_time;//修改时间
    private String fd_add1;//备用字段
    private String fd_add2;//备用字段
    private String cComUnitName;
}
