package com.mt.controller.standard.offer;

import com.alibaba.fastjson.JSONObject;

import com.mt.mapper.standard.offer.Pur_OrderMapper;
import com.mt.pojo.standard.cy_order;
import com.mt.pojo.standard.cy_order_detailed;
import com.mt.util.JSONUtil;
import com.mt.util.ObjectMapperUtil;
import com.mt.util.PushUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class Pur_Order {
    @Autowired
    private Pur_OrderMapper mapper;

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
            Random random = new Random();
            String result = "MTCGHT-Z";
            for (int i = 0; i < 8; i++) {
                //首字母不能为0
                result += (random.nextInt(9) + 1);
            }

            String requestXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<ufinterface sender=\"001\" receiver=\"u8\" roottag=\"purchaseorder\" docid=\"885992348\" proc=\"add\" codeexchanged=\"N\" exportneedexch=\"N\" paginate=\"0\" display=\"采购订单\" family=\"采购管理\">" +
                    "<purchaseorder>" +
                    "<header>" +
                    "<code>" + result + "</code>" +
                    "<date>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "</date>" +
                    "<vendorcode>" + detailedList.get(0).getFd_supplier_code() + "</vendorcode>" +
                    "<deptcode>2001</deptcode>" +
                    "<personcode/>" +
                    "<purchase_type_code>01</purchase_type_code>" +
                    "<operation_type_code>普通采购</operation_type_code>" +
                    "<address/>" +
                    "<idiscounttaxtype>0</idiscounttaxtype>" +
                    "<recsend_type/>" +
                    "<currency_name>人民币</currency_name>" +
                    "<currency_rate>1</currency_rate>" +
                    "<tax_rate>" + detailedList.get(0).getFd_tax() + "</tax_rate>" +
                    "<paycondition_code/>" +
                    "<traffic_money>0</traffic_money>" +
                    "<bargain>0</bargain>" +
//                    "<cappcode></cappcode>" +
                    "<remark/>" +
                    "<period/>" +
                    "<maker>demo</maker>" +
                    "</header>" +
                    "<body>" +
                    "<entry>" +
                    "<inventorycode>" + detailedList.get(0).getFd_inventory_no() + "</inventorycode>" +
                    "<checkflag>0</checkflag>" +
                    "<unitcode>" + detailedList.get(0).getFd_unit() + "</unitcode>" +
                    "<quantity>" + detailedList.get(0).getFd_purchase_avaqty() + "</quantity>" +
                    "<num>9</num>" +
                    "<quotedprice/>" +
//                    "<price>59.83</price>" +
                    "<taxprice>" + detailedList.get(0).getFd_offer() + "</taxprice>" +
//                    "<money>26923.08</money>" +
//                    "<tax>4576.92</tax>" +
//                    "<sum>31500</sum>" +
//                    "<discount/>" +
//                    "<natprice>59.83</natprice>" +
//                    "<natmoney>26923.08</natmoney>" +
//                    "<assistantunit>0502</assistantunit>" +
//                    "<nattax>4576.92</nattax>" +
//                    "<natsum>31500</natsum>" +
//                    "<natdiscount/>" +
                    "<taxrate>" + detailedList.get(0).getFd_tax() + "</taxrate>" +
                    "<item_class/>" +
                    "<item_code/>" +
                    "<item_name/>" +
                    "<arrivedate>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "</arrivedate>" +
                    "</entry>" +
                    "</body>" +
                    "</purchaseorder>" +
                    "</ufinterface>";
            PushUtil.push(requestXml);
            return JSONUtil.toJson("0", "", "推送成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "推送失败！", "");
        }
    }
}
