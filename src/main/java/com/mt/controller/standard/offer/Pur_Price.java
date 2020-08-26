package com.mt.controller.standard.offer;

import com.alibaba.fastjson.JSONObject;
import com.mt.mapper.standard.Pur_PriceMapper;
import com.mt.pojo.standard.cy_supplier_deliver;
import com.mt.pojo.standard.cy_supplier_deliver_details;
import com.mt.util.IDUtil;
import com.mt.util.JSONUtil;
import com.mt.util.PushUtil;
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
public class Pur_Price {
    @Autowired
    private Pur_PriceMapper mapper;

    @RequestMapping("Delivery_MainTable")
    @ResponseBody
    public String Delivery_MainTable(String name, String type) {
        try {
            if (type.equals("供应商")) {
                List<cy_supplier_deliver> list = mapper.select_deliver(null, name);
                return JSONUtil.toJson("0", list, "获取成功！", "");
            } else {
                List<cy_supplier_deliver> list = mapper.select_deliver(name, null);
                return JSONUtil.toJson("0", list, "获取成功！", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "获取失败！", "");
        }
    }

    @RequestMapping("Delivery_SubTable")
    @ResponseBody
    public String Delivery_SubTable(String fd_parent_id) {
        try {
            List<cy_supplier_deliver_details> list = mapper.select_detail(fd_parent_id);
            return JSONUtil.toJson("0", list, "获取成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "获取失败！", "");
        }
    }


    @RequestMapping("PushToTheInvoice")
    @ResponseBody
    public String PushToTheInvoice(@RequestBody String data) {
        try {
            System.out.println(data);
            //子表数据
            List<cy_supplier_deliver_details> deliver_details = JSONObject.parseArray(
                    data, cy_supplier_deliver_details.class);
            //查询主表数据
            List<cy_supplier_deliver> cySupplierDelivers = mapper.find_detail(
                    deliver_details.get(0).getFd_parent_id());

            //生成到货单主表ID
            Random random = new Random();
            String result = "MTCGHT-Z";
            for (int k = 0; k < 8; k++) {
                //首字母不能为0
                result += (random.nextInt(9) + 1);
            }


            for (int i = 0; i < cySupplierDelivers.size(); i++) {
                String uuid = IDUtil.getUUID();
                String requestXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                        "<ufinterface sender=\"001\" receiver=\"u8\" roottag=\"arrivedgoods\" proc=\"add\" codeexchanged=\"N\" exportneedexch=\"N\" paginate=\"0\" display=\"采购到货单\" family=\"采购管理\">" +
                        "<arrivedgoods>" +
                        "<header tablename=\"pu_arrivalvouch\">" +
                        "<code>" + result + "</code>" +
                        "<businesstype>普通采购</businesstype>" +
                        "<purchasetypecode>01</purchasetypecode>" +
                        "<date>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "</date>" +
                        "<vendorcode>" + cySupplierDelivers.get(i).getFd_supplier_code() + "</vendorcode>" +
                        "<departmentcode>2101</departmentcode>" +
                        "<personcode>" + cySupplierDelivers.get(i).getCPersonCode() + "</personcode>" +
                        "<payconditioncode/>" +
                        "<foreigncurrency>人民币</foreigncurrency>" +
                        "<foreigncurrencyrate>1</foreigncurrencyrate>" +
                        "<idiscounttaxtype>" + deliver_details.get(0).getFd_tax() + "</idiscounttaxtype>" +
                        "<shipcode/>" +
                        "<memory/>" +
                        "<maker>demo</maker>" +
                        "<rejecttag>0</rejecttag>" +
                        "<define1>" + cySupplierDelivers.get(i).getFd_purorder_no() + "</define1>" +
                        "<billtype>0</billtype>" +
                        "</header><body>";
                for (int j = 0; j < deliver_details.size(); j++) {
                    if (cySupplierDelivers.get(i).getFd_id().equals(deliver_details.get(j).getFd_parent_id())) {
                        requestXml += "<entry>" +
                                "<inventorycode>" + deliver_details.get(j).getFd_inventory_no() + "</inventorycode>" +
                                "<number>9</number>" +
                                "<quantity>" + deliver_details.get(j).getFd_shipment_qty() + "</quantity>" +
                                "<taxrate>" + deliver_details.get(j).getFd_tax() + "</taxrate>" +
                                "<ischecked>0</ischecked>" +
                                "<unitid>" + deliver_details.get(j).getFd_unit() + "</unitid>" +
                                "<refusenumber/>" +
                                "<refusequantity/>" +
                                "</entry>";

                        mapper.update_number(deliver_details.get(j).getFd_shipment_qty()
                                , deliver_details.get(j).getFd_id());
                    }
                }
                requestXml = requestXml + "</body></arrivedgoods></ufinterface>";
                System.out.println("requestXml:" + requestXml);
                PushUtil.push(requestXml);
            }

            return JSONUtil.toJson("0", "", "获取成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "获取失败！", "");
        }
    }
}
