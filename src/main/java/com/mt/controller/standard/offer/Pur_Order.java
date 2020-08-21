package com.mt.controller.standard.offer;

import com.alibaba.fastjson.JSONObject;
import com.mt.mapper.standard.offer.Pur_OrderMapper;
import com.mt.pojo.standard.cy_order;
import com.mt.pojo.standard.cy_order_detailed;
import com.mt.util.JSONUtil;
import com.mt.util.ObjectMapperUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public String Inquiry_Quotation(String name, String type) {
        try {
            //判断人员查询的类型
            if (type.equals("供应商")) {
                System.out.println("供应商：" + name);
                List<cy_order> list = mapper.Select_order(null, name);
                return JSONUtil.toJson("0", list, "获取成功！", "");
            } else {
                //采购员/技术员
                List<cy_order> list = mapper.Select_order(name, null);
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
    private String Inquiry_Quotation_Sublist(String fd_id) {
        try {
            List<cy_order_detailed> list = mapper.Select_orderson(fd_id);
            return JSONUtil.toJson("0", list, "获取成功！", "");
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
}
