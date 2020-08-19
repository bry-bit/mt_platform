package com.mt.pojo.standard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 历史价格库
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_historical_price {
    private String fd_id;//ID
    private String fd_offer_date;//报价日期
    private String fd_supplier_name;//供应商名称
    private String fd_supplier_code;//供应商编码
    private String fd_model;//规格型号
    private String fd_inventory_no;//存货编号
    private String fd_inventory_name;//存货名称
    private String fd_unit;//单位
    private String fd_number;//数量
    private String fd_tax;//税率
    private String fd_offer;//报价
    private String fd_record_status;//记录状态（询比价记录、成交记录）
    private String fd_data_sources;//数据来源（供应商平台、U8）
    private String fd_remarks;//备注
    private String fd_creat_person;//创建人
    private String fd_creat_time;//创建时间
    private String fd_modificatio_person;//修改人
    private String fd_modificatio_time;//修改时间
    private String fd_add1;//备用字段
    private String fd_add2;//备用字段
}
