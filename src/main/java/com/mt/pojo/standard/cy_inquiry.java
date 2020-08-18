package com.mt.pojo.standard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;

/**
 * 供应商平台标准流程化询价单
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_inquiry {
    //ID
    @Column(name = "fd_id")
    private String fd_id;
    //询价单号
    @Column(name = "fd_no")
    private String fd_no;
    //制单人
    @Column(name = "fd_creat_person")
    private String fd_creat_person;
    //创建时间
    @Column(name = "fd_creat_time")
    private String fd_creat_time;
    //修改人
    @Column(name = "fd_modificatio_person")
    private String fd_modificatio_person;
    //修改时间
    @Column(name = "fd_modificatio_time")
    private String fd_modificatio_time;
    //U8请购单ID
    @Column(name = "fd_inquiryid")
    private String fd_inquiryid;
    //U8请购单单号
    @Column(name = "fd_apply_no")
    private String fd_apply_no;
    //U8请购单日期
    @Column(name = "fd_apply_time")
    private String fd_apply_time;
    //U8请购单部门
    @Column(name = "fd_apply_department")
    private String fd_apply_department;
    //U8业务员
    @Column(name = "fd_applicant")
    private String fd_applicant;
    //U8请购单业务类型
    @Column(name = "fd_apply_bustype")
    private String fd_apply_bustype;
    //U8请购单采购类型
    @Column(name = "fd_apply_purtype")
    private String fd_apply_purtype;
    //开标时间
    @Column(name = "fd_bid_opentime")
    private String fd_bid_opentime;
    //截标时间
    @Column(name = "fd_bid_closetime")
    private String fd_bid_closetime;
    //供应商名称
    @Column(name = "fd_supplier_name")
    private String fd_supplier_name;
    //供应商编码
    @Column(name = "fd_supplier_code")
    private String fd_supplier_code;
    //可采购数量
    @Column(name = "fd_purchase_avaqty")
    private String fd_purchase_avaqty;
    //备注
    @Column(name = "fd_remarks")
    private String fd_remarks;
    //子表操作键
    @Column(name = "fd_edit")
    private String fd_edit;
    //发布状态
    @Column(name = "fd_status")
    private String fd_status;
    //备用字段
    @Column(name = "fd_add1")
    private String fd_add1;
    //备用字段
    @Column(name = "fd_add2")
    private String fd_add2;
    //备用字段
    @Column(name = "fd_add3")
    private String fd_add3;
    //备用字段
    @Column(name = "fd_add4")
    private String fd_add4;
    private String cDepName;
    private String cPTName;
    private String sum;
    private String suppliername;
    private String cPersonName;
}
