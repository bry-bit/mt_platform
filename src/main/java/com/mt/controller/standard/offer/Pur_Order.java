package com.mt.controller.standard.offer;

import com.alibaba.fastjson.JSONObject;

import com.mt.mapper.standard.Pur_PriceMapper;
import com.mt.mapper.standard.offer.Pur_OrderMapper;
import com.mt.pojo.standard.*;
import com.mt.util.IDUtil;
import com.mt.util.JSONUtil;
import com.mt.util.ObjectMapperUtil;
import com.mt.util.PushUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 供应商报价、中标审核等
 */
@Controller
public class Pur_Order {
    @Autowired
    private Pur_OrderMapper mapper;
    @Autowired
    private Pur_PriceMapper priceMapper;

    /**
     * 查询报价单主表数据
     *
     * @param name 登录人名称
     * @param type 登陆人类型
     * @return
     */
    @RequestMapping("Inquiry_Quotation")
    @ResponseBody
    public String Inquiry_Quotation(String name, String type, String fd_quotation_tatus) {
        try {

            //判断人员查询的类型
            if (type.equals("供应商")) {
                System.out.println(name + "," + type + "," + fd_quotation_tatus);
                List<cy_order> list = mapper.Select_order(null, name, fd_quotation_tatus);
                System.out.println("主表数据：" + list);
                return JSONUtil.toJson("0", list, "获取成功！", "");
            } else {
                System.out.println(name + "," + type + "," + fd_quotation_tatus);
                //采购员/技术员
                List<cy_order> list = mapper.Select_order(name, null, fd_quotation_tatus);
                System.out.println("主表数据：" + list);
                return JSONUtil.toJson("0", list, "获取成功！", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "获取失败！", "");
        }
    }

    /**
     * 查询报价单子表
     *
     * @param fd_id 报价单主表ID
     * @return
     */
    @RequestMapping("Inquiry_Quotation_Sublist")
    @ResponseBody
    private String Inquiry_Quotation_Sublist(String fd_id, String fd_quotation_tatus
            , String name, String type) {
        try {
            System.out.println("主表fd_id：" + fd_id);
            System.out.println("主表报价状态：" + fd_quotation_tatus);
            System.out.println("登陆状态：" + type);
            System.out.println("登陆人：" + name);
            //判断人员查询的类型
            if (type.equals("供应商")) {
                System.out.println("供应商：" + name);
                List<cy_order_detailed> list = mapper.Select_orderson(fd_id, null, name, fd_quotation_tatus);
                return JSONUtil.toJson("0", list, "获取成功！", "");
            } else {
                List<cy_order_detailed> list = mapper.Select_orderson(fd_id, name, null, fd_quotation_tatus);
                return JSONUtil.toJson("0", list, "获取成功！", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "获取失败！", "");
        }
    }

    /**
     * 供应商进行报价
     *
     * @param data 报价数据
     * @return
     */
    @RequestMapping("Supplier_Quotation")
    @ResponseBody
    public String Supplier_Quotation(@RequestBody String data) {
        try {
            if (StringUtils.isNotBlank(data)) {
                data = data.trim();
                if (data.startsWith("{") && data.endsWith("}")) {
                    cy_order_detailed detailed = ObjectMapperUtil.toObject(data, cy_order_detailed.class);
                    mapper.order_sonUpdate(detailed);
                } else {
                    List<cy_order_detailed> detailedList = JSONObject.parseArray(data, cy_order_detailed.class);
                    for (int i = 0; i < detailedList.size(); i++) {
                        cy_order_detailed detailed = JSONObject.parseObject(
                                JSONObject.toJSONString(detailedList.get(i)), cy_order_detailed.class);
                        mapper.order_sonUpdate(detailed);
                    }
                }
            }

            return JSONUtil.toJson("0", "", "报价成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "报价失败！", "");
        }
    }

    /**
     * 报价审核列表查询
     *
     * @param name
     * @return
     */
    @RequestMapping("OfferReview")
    @ResponseBody
    public String OfferReview(String name) {
        try {
            System.out.println("审核人的name:" + name);
            List<cy_order_detailed> list = mapper.audit(name);
            return JSONUtil.toJson("0", list, "查询成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "查询失败！", "");
        }
    }

    /**
     * 退回重新报价
     *
     * @param data
     * @param msg
     * @return
     */
    @PostMapping("/backSubquotation")
    @ResponseBody
    public String backSubquotation(@RequestBody String data, String msg) {
        try {
            List<cy_order_detailed> list = JSONObject.parseArray(data, cy_order_detailed.class);
            for (int i = 0; i < list.size(); i++) {
                cy_order_detailed parseObject = JSONObject.parseObject(
                        JSONObject.toJSONString(list.get(i)), cy_order_detailed.class);
                parseObject.setFd_return_reason(msg);
                parseObject.setFd_return("1");

                mapper.order_sonUpdate(parseObject);
            }

            return JSONUtil.toJson("0", "", "退回成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "退回失败！", "");
        }
    }


    /**
     * 推送采购订单
     *
     * @param data
     * @return
     */
    @RequestMapping("Push_Purchase_Order")
    @ResponseBody
    public String Push_Purchase_Order(@RequestBody String data) {
        try {
            System.out.println("推送的数据：" + data);
            List<cy_order_detailed> detailedList = JSONObject.parseArray(data, cy_order_detailed.class);
            System.out.println("整合：" + detailedList);
            List<String> nameList = new ArrayList<String>();
            //将已选择的供应商放入到nameList集合中
            for (cy_order_detailed detailed : detailedList) {
                String fd_supplier_code = detailed.getFd_supplier_code();
                if (!nameList.contains(fd_supplier_code)) {
                    // 将未包含的元素添加进listTemp集合中
                    nameList.add(fd_supplier_code);
                }
            }
            System.out.println("nameList:" + nameList);
            //判断nameList是否为空，不空则进行下一步
            if (nameList != null) {
                //取出nameList中的供应商
                for (String fd_supplier_code : nameList) {
                    System.out.println("fd_supplier_code:" + fd_supplier_code);
                    //生成采购订单编号
                    Random random = new Random();
                    String result = "MTCGHT-Z";
                    for (int k = 0; k < 8; k++) {
                        //首字母不能为0
                        result += (random.nextInt(9) + 1);
                    }


                    //生成主表数据
                    String requestXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                            + "<ufinterface sender=\"001\" receiver=\"u8\" roottag=\"purchaseorder\" docid=\"885992348\" proc=\"add\" codeexchanged=\"N\" exportneedexch=\"N\" paginate=\"0\" display=\"采购订单\" family=\"采购管理\">"
                            + "<purchaseorder>" + "<header>"
                            + "<code>" + result + "</code>"
                            + "<date>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "</date>"
                            + "<vendorcode>" + fd_supplier_code + "</vendorcode>"
                            + "<deptcode>2101</deptcode>"
                            + "<personcode>" + detailedList.get(0).getCPersonCode() + "</personcode>"
                            + "<purchase_type_code>01</purchase_type_code>"
                            + "<operation_type_code>普通采购</operation_type_code>"
                            + "<address/>"
                            + "<idiscounttaxtype>0</idiscounttaxtype>"
                            + "<recsend_type/>"
                            + "<currency_name>人民币</currency_name>"
                            + "<currency_rate>1</currency_rate>"
                            + "<tax_rate>" + detailedList.get(0).getFd_tax() + "</tax_rate>"
                            + "<paycondition_code/>"
                            + "<traffic_money>0</traffic_money>"
                            + "<bargain>0</bargain>"
                            + "<remark/>"
                            + "<period/>"
                            + "<maker>demo</maker>"
                            + "</header>"
                            + "<body>";

                    //生成发货单主表
                    String uuid = IDUtil.getUUID();

                    String fd_supplier_name = mapper.SelSup_name(fd_supplier_code);
                    cy_supplier_deliver cySupplierDeliver = new cy_supplier_deliver();
                    cySupplierDeliver.setFd_id(uuid);
                    cySupplierDeliver.setFd_supplier_name(fd_supplier_name);
                    cySupplierDeliver.setFd_supplier_code(fd_supplier_code);
                    cySupplierDeliver.setFd_purorder_no(result);
                    cySupplierDeliver.setFd_creat_person(detailedList.get(0).getFd_order_person());
                    if (cySupplierDeliver.getFd_supplier_name() != ""
                            || cySupplierDeliver.getFd_supplier_name() != null) {
                        priceMapper.Insert_deliver(cySupplierDeliver);
                    } else {
                        continue;
                    }

                    //进行子表数据循环
                    for (int i = 0; i < detailedList.size(); i++) {
                        //判断该供应商和子表数据中的供应商是否一样，一样则推送采购订单
                        if (fd_supplier_code.equals(detailedList.get(i).getFd_supplier_code())) {
//                            System.out.println(detailedList.get(i).getFd_supplier_name());
                            System.out.println(detailedList.get(i));
                            requestXml += "<entry>" +
                                    "<inventorycode>" + detailedList.get(i).getFd_inventory_no() + "</inventorycode>" +
                                    "<checkflag>0</checkflag>" +
                                    "<unitcode>" + detailedList.get(i).getFd_unit() + "</unitcode>" +
                                    "<quantity>" + detailedList.get(i).getFd_purchase_avaqty() + "</quantity>" +
                                    "<quotedprice/>" +
                                    "<taxprice>" + detailedList.get(i).getFd_offer() + "</taxprice>" +
                                    "<discount/>" +
                                    "<taxrate>" + detailedList.get(i).getFd_tax() + "</taxrate>" +
                                    "<item_name>" + detailedList.get(i).getFd_arrival_house() + "</item_name>" +
                                    "<arrivedate>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "</arrivedate>" +
                                    "<define28>" + detailedList.get(i).getFd_inquiry_ids() + "</define28>" +
                                    "</entry>";


                            //生成发货子表
                            String uuid1 = IDUtil.getUUID();

                            cy_supplier_deliver_details deliverDetails = new cy_supplier_deliver_details();
                            deliverDetails.setFd_id(uuid1);//ID
                            deliverDetails.setFd_parent_id(cySupplierDeliver.getFd_id());//关联主表ID
                            deliverDetails.setFd_order_ids(detailedList.get(i).getFd_id());//报价单子表id
                            deliverDetails.setFd_order_person(detailedList.get(i).getFd_order_person());//采购员
                            deliverDetails.setFd_inventory_no(detailedList.get(i).getFd_inventory_no());//存货编号
                            deliverDetails.setFd_inventory_name(detailedList.get(i).getFd_inventory_name());//存货名称
                            deliverDetails.setFd_model(detailedList.get(i).getFd_model());//规格型号
                            deliverDetails.setFd_unit(detailedList.get(i).getFd_unit());//主计量单位
                            deliverDetails.setFd_purchase_avaqty(detailedList.get(i).getFd_purchase_avaqty());//可采购数量
                            deliverDetails.setFd_tax(detailedList.get(i).getFd_tax());//税率
                            deliverDetails.setFd_ship_addr(detailedList.get(i).getFd_ship_addr());//发货地址
                            deliverDetails.setFd_arrival_house(detailedList.get(i).getFd_arrival_house());//到货仓库

                            priceMapper.insert_detail(deliverDetails);

                        } else {
                            continue;
                        }
                    }
                    requestXml = requestXml + "</body></purchaseorder></ufinterface>";

                    System.out.println("requestXml:" + requestXml);
                    PushUtil.push(requestXml);

                    if (detailedList.get(0).getFd_offer() != null || detailedList.get(0).getFd_offer() != "") {
                        for (int i = 0; i < detailedList.size(); i++) {
                            cy_order_detailed parseObject = JSONObject.parseObject(
                                    JSONObject.toJSONString(detailedList.get(i)), cy_order_detailed.class);
                            parseObject.setFd_quotation_tatus("2");
                            mapper.order_sonUpdate(parseObject);
                            mapper.lose_a_bid(parseObject);
                        }
                    }
                }
            }
            return JSONUtil.toJson("0", "", "推送成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "推送失败！", "");
        }
    }


}
