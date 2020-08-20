package com.mt.controller.frame;

import com.alibaba.fastjson.JSONObject;
import com.mt.mapper.standard.Pur_EnquiryMapper;
import com.mt.mapper.standard.Pur_SelectSupMapper;
import com.mt.pojo.standard.*;
import com.mt.util.IDUtil;
import com.mt.util.JSONUtil;
import com.mt.util.ObjectMapperUtil;
import com.mt.util.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class Frame_Inquiry {
    @Autowired
    private Pur_EnquiryMapper mapper;
    @Autowired
    private Pur_SelectSupMapper supMapper;

    @Resource
    RandomUtil randomUtil;

    /**
     * 查询询价单主表数据
     *
     * @param fd_applicant
     * @return
     */
    @RequestMapping("InquirySheet")
    @ResponseBody
    public String InquirySheet(String fd_applicant) {
        try {
            List<cy_inquiry> inquiryList = mapper.Main_sheet(fd_applicant, null);

            return JSONUtil.toJson("0", inquiryList, "获取成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "获取失败！", "");
        }
    }

    /**
     * 该请购单是否由子表子表询价
     *
     * @param data
     * @param fd_edit
     * @return
     */
    @RequestMapping("ChildInquiry")
    @ResponseBody
    public String ChildInquiry(@RequestBody String data, String fd_edit) {
        try {
            System.out.println(data);
            if (StringUtils.isNotBlank(data)) {
                data = data.trim();
                if (data.startsWith("{") && data.endsWith("}")) {
                    cy_inquiry inquiry = ObjectMapperUtil.toObject(data, cy_inquiry.class);
                    mapper.update(fd_edit, inquiry.getFd_id());
                } else {
                    List<cy_inquiry> inquiryList = JSONObject.parseArray(data, cy_inquiry.class);
                    for (int i = 0; i < inquiryList.size(); i++) {
                        mapper.update(fd_edit, inquiryList.get(i).getFd_id());
                    }
                }
            }

            return JSONUtil.toJson("0", "", "提交成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "提交失败！", "");
        }
    }

    /**
     * 查询子表数据
     *
     * @param fd_parent_id 主表ID
     * @return
     */
    @RequestMapping("QueryChildTable")
    @ResponseBody
    public String QueryChildTable(String fd_parent_id) {
        try {
            System.out.println("fd_parent_id:" + fd_parent_id);
            List<cy_inquiry_detailed> list = mapper.Person_inqdata(fd_parent_id);
            return JSONUtil.toJson("0", list, "获取成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "获取失败！", "");
        }
    }


    @RequestMapping("Generating_Quotation")
    @ResponseBody
    public String Generating_Quotation(@RequestBody String data, String table, String name) {
        try {
            System.out.println(data);

            //判断传值的为主表还是子表
            if (table.equals("primary")) {
                //主表数据循环
                List<cy_inquiry> cyInquiryList = JSONObject.parseArray(data, cy_inquiry.class);
                for (int i = 0; i < cyInquiryList.size(); i++) {
                    String fd_no = randomUtil.getNewAutoNum();
                    //循环已选择的供应商
                    List<cy_supplier> cySuppliers = supMapper.select_supp(
                            cyInquiryList.get(i).getFd_no(), null, null);
                    for (int j = 0; j < cySuppliers.size(); j++) {
                        String uuid = IDUtil.getUUID();
                        cy_order cyOrder = new cy_order();
                        cyOrder.setFd_id(uuid);
                        cyOrder.setFd_no(fd_no);
                        cyOrder.setFd_applicant(cyInquiryList.get(i).getCPersonName());
                        cyOrder.setFd_creat_person(cyInquiryList.get(i).getCPersonName());
                        cyOrder.setFd_creat_time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        cyOrder.setFd_apply_no(cyInquiryList.get(i).getFd_apply_no());
                        cyOrder.setFd_apply_time(cyInquiryList.get(i).getFd_apply_time());
                        cyOrder.setFd_apply_department(cyInquiryList.get(i).getCDepName());
                        cyOrder.setFd_apply_bustype(cyInquiryList.get(i).getFd_apply_bustype());
                        cyOrder.setFd_apply_purtype(cyInquiryList.get(i).getFd_apply_purtype());
                        cyOrder.setFd_purchase_avaqty(cyInquiryList.get(i).getFd_purchase_avaqty());
                        cyOrder.setFd_inquiryid(cyInquiryList.get(i).getFd_id());
                        cyOrder.setFd_supplier_name(cySuppliers.get(j).getSuppliername());
                        cyOrder.setFd_supplier_code(cySuppliers.get(j).getSuppliercode());
                        cyOrder.setFd_bid_opentime(cySuppliers.get(j).getStartdate());
                        cyOrder.setFd_bid_closetime(cySuppliers.get(j).getEnddate());

                        //报价主表插入
                        mapper.Main_order(cyOrder);

                        //循环子表
                        List<cy_inquiry_detailed> cyInquiryDetaileds = mapper.Person_inqdata(cyInquiryList.get(i).getFd_id());
                        for (int k = 0; k < cyInquiryDetaileds.size(); k++) {
                            String uuid1 = IDUtil.getUUID();
                            cy_order_detailed cyOrderDetailed = new cy_order_detailed();
                            cyOrderDetailed.setFd_id(uuid1);
                            cyOrderDetailed.setFd_parent_id(cyOrder.getFd_id());
                            cyOrderDetailed.setFd_inquiry_ids(cyInquiryDetaileds.get(k).getFd_id());
                            cyOrderDetailed.setFd_inventory_name(cyInquiryDetaileds.get(k).getFd_inventory_name());
                            cyOrderDetailed.setFd_inventory_no(cyInquiryDetaileds.get(k).getFd_inventory_no());
                            cyOrderDetailed.setFd_model(cyInquiryDetaileds.get(k).getFd_model());
                            cyOrderDetailed.setFd_unit(cyInquiryDetaileds.get(k).getFd_unit());
                            cyOrderDetailed.setFd_purchase_avaqty(cyInquiryDetaileds.get(k).getFd_purchase_avaqty());
                            cyOrderDetailed.setFd_tax(cyInquiryDetaileds.get(k).getFd_tax());
                            //报价子表插入
                            mapper.Person_order(cyOrderDetailed);
                        }
                    }

                    //修改发布后的询价单状态
                    cy_inquiry cyInquiry = JSONObject.parseObject(
                            JSONObject.toJSONString(cyInquiryList.get(i)), cy_inquiry.class);
                    cyInquiry.setFd_status("1");
                    mapper.Main_inquiry(cyInquiry);
                }
            } else {
                //传子表数据
                List<cy_inquiry_detailed> cyInquiryDetailedList = JSONObject.parseArray(data, cy_inquiry_detailed.class);
                //查询主表数据
                List<cy_inquiry> cyInquiryList = mapper.Main_sheet(name, cyInquiryDetailedList.get(0).getFd_parent_id());
                for (int i = 0; i < cyInquiryList.size(); i++) {
                    cy_order cyOrder = new cy_order();
                    cyOrder.setFd_id(IDUtil.getUUID());
                    cyOrder.setFd_no(randomUtil.getNewAutoNum());
                    cyOrder.setFd_applicant(cyInquiryList.get(i).getCPersonName());
                    cyOrder.setFd_creat_person(cyInquiryList.get(i).getCPersonName());
                    cyOrder.setFd_creat_time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    cyOrder.setFd_apply_no(cyInquiryList.get(i).getFd_apply_no());
                    cyOrder.setFd_apply_time(cyInquiryList.get(i).getFd_apply_time());
                    cyOrder.setFd_apply_department(cyInquiryList.get(i).getCDepName());
                    cyOrder.setFd_apply_bustype(cyInquiryList.get(i).getFd_apply_bustype());
                    cyOrder.setFd_apply_purtype(cyInquiryList.get(i).getFd_apply_purtype());
                    cyOrder.setFd_purchase_avaqty(cyInquiryList.get(i).getFd_purchase_avaqty());
                    cyOrder.setFd_inquiryid(cyInquiryList.get(i).getFd_id());

                    for (int j = 0; j < cyInquiryDetailedList.size(); j++) {
                        //循环已选择的供应商
                        List<cy_supplier> cySuppliers = supMapper.select_supp(
                                cyInquiryList.get(i).getFd_id(), null, null);


                    }

                }
            }


            return JSONUtil.toJson("0", "", "生成报价单成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "生成报价单失败！", "");
        }
    }
}
