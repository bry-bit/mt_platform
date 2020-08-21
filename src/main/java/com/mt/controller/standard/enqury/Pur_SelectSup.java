package com.mt.controller.standard.enqury;

import com.alibaba.fastjson.JSONObject;
import com.mt.mapper.standard.enqury.Pur_SelectSupMapper;
import com.mt.pojo.standard.cy_inquiry;
import com.mt.pojo.standard.cy_inquiry_detailed;
import com.mt.pojo.standard.cy_supplier;
import com.mt.util.JSONUtil;
import com.mt.util.ObjectMapperUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 选择供应商列表
 */
@Controller
public class Pur_SelectSup {
    @Autowired
    private Pur_SelectSupMapper mapper;

    /**
     * 查询供应商档案
     *
     * @param page  页数
     * @param limit 一页的数量
     * @return
     */
    @RequestMapping("SupplierList")
    @ResponseBody
    public String SupplierList(Integer page, Integer limit) {
        try {
            //查询
            List<cy_supplier> list = mapper.Chose_sup();
            //分页
            List<cy_supplier> supplierList = mapper.Chose_sup_page(page, limit);

            return JSONUtil.toJson("0", supplierList, "获取成功！", list.size());
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "获取失败！", "");
        }
    }

    /**
     * 选中供应商后进行插入到供应商列表中
     *
     * @param data  表中数据、供应商信息
     * @param table 主子表的标识（主：primary/子：sublist）
     * @return
     */
    @RequestMapping("InsertTable")
    @ResponseBody
    public String InsertTable(@RequestBody String data, String table) {
        try {
            //将两个json传进行分离
            JSONObject object = JSONObject.parseObject(data);
            String supList = object.getString("list");
            String chooseData = object.getString("data").replaceAll("\\\\", "");

            //供应商列表循环
            List<cy_supplier> list = JSONObject.parseArray(supList, cy_supplier.class);

            //判断传值的为主表还是子表
            if (table.equals("primary")) {
                //主表
                List<cy_inquiry> dataList = JSONObject.parseArray(chooseData, cy_inquiry.class);
                for (int i = 0; i < dataList.size(); i++) {
                    //供应商列表的ID
                    cy_supplier cySupplier = new cy_supplier();
                    cySupplier.setID(dataList.get(i).getFd_no());
                    //循环插入的供应商信息
                    for (int j = 0; j < list.size(); j++) {
                        //查询该ID下是否存在该供应商
                        List<cy_supplier> suppliers = mapper.select_supp(dataList.get(i).getFd_no()
                                , list.get(j).getSuppliername(), "1");
                        //存在
                        if (suppliers.size() > 0) {
                            return JSONUtil.toJson("2", "", "请购单号为" + dataList.get(j).getFd_inquiryid()
                                    + "存在" + list.get(i).getSuppliername() + "！", "");
                        } else {
                            //不存在
                            cySupplier.setSuppliername(list.get(j).getSuppliername());
                            cySupplier.setSuppliercode(list.get(j).getSuppliercode());
                            cySupplier.setStartdate(list.get(j).getStartdate());
                            cySupplier.setEnddate(list.get(j).getEnddate());
                            mapper.addsupp(cySupplier);
                        }
                    }
                }
            } else {
                //子表
                List<cy_inquiry_detailed> dataList = JSONObject.parseArray(chooseData, cy_inquiry_detailed.class);
                for (int i = 0; i < dataList.size(); i++) {
                    //供应商列表的ID
                    cy_supplier cySupplier = new cy_supplier();
                    cySupplier.setID(dataList.get(i).getFd_id());
                    //循环插入的供应商信息
                    for (int j = 0; j < list.size(); j++) {
                        //查询该ID下是否存在该供应商
                        List<cy_supplier> suppliers = mapper.select_supp(dataList.get(i).getFd_id()
                                , list.get(j).getSuppliername(), "1");
                        //存在
                        if (suppliers.size() > 0) {
                            return JSONUtil.toJson("2", "", "存货名称为" + dataList.get(i).getFd_inventory_name()
                                    + "(" + dataList.get(i).getFd_inventory_no() + ")存在" + list.get(i).getSuppliername() + "！", "");
                        } else {
                            //不存在
                            cySupplier.setSuppliername(list.get(j).getSuppliername());
                            cySupplier.setSuppliercode(list.get(j).getSuppliercode());
                            cySupplier.setStartdate(list.get(j).getStartdate());
                            cySupplier.setEnddate(list.get(j).getEnddate());
                            mapper.addsupp(cySupplier);
                        }
                    }
                }
            }

            return JSONUtil.toJson("0", "", "插入成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "插入失败！", "");
        }
    }

    /**
     * 查询已选择供应商
     *
     * @param ID 关联主子表ID
     * @return
     */
    @RequestMapping("ExistingSupplier")
    @ResponseBody
    public String ExistingSupplier(String ID) {
        try {
            System.out.println("ID:" + ID);
            List<cy_supplier> supplierList = mapper.select_supp(ID, null, null);

            return JSONUtil.toJson("0", supplierList, "查询成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "查询失败！", "");
        }
    }

    /**
     * 修改供应商开标、结标时间
     *
     * @param data 需修改时间的供应商数据
     * @return
     */
    @RequestMapping("DateChanged")
    @ResponseBody
    public String DateChanged(@RequestBody String data) {
        try {
            if (StringUtils.isNotBlank(data)) {
                data = data.trim();
                if (data.startsWith("{") && data.endsWith("}")) {
                    cy_supplier cySupplier = ObjectMapperUtil.toObject(data, cy_supplier.class);
                    System.out.println("cySupplier(单):" + cySupplier);
                    mapper.update_time(cySupplier);
                } else {
                    List<cy_supplier> inquiryList = JSONObject.parseArray(data, cy_supplier.class);
                    for (int i = 0; i < inquiryList.size(); i++) {
                        cy_supplier cySupplier = JSONObject.parseObject(
                                JSONObject.toJSONString(inquiryList.get(i)), cy_supplier.class);
                        System.out.println("cySupplier(双):" + cySupplier);
                        mapper.update_time(cySupplier);
                    }
                }
            }

            return JSONUtil.toJson("0", "", "修改成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "修改失败！", "");
        }
    }

    /**
     * 删除已选择的供应商
     *
     * @param data 要删除供应商的数据
     * @return
     */
    @RequestMapping("Delete_Supplier")
    @ResponseBody
    public String Delete_Supplier(@RequestBody String data) {
        try {
            System.out.println("删除的data:" + data);
            if (StringUtils.isNotBlank(data)) {
                data = data.trim();
                if (data.startsWith("{") && data.endsWith("}")) {
                    String ID = JSONObject.parseObject(data).getString("iD");
                    cy_supplier cySupplier = ObjectMapperUtil.toObject(data, cy_supplier.class);
                    cySupplier.setID(ID);
                    System.out.println("cySupplier(单):" + cySupplier);
                    mapper.delete_message(cySupplier);
                } else {
                    List<cy_supplier> inquiryList = JSONObject.parseArray(data, cy_supplier.class);
                    for (int i = 0; i < inquiryList.size(); i++) {
                        cy_supplier cySupplier = JSONObject.parseObject(
                                JSONObject.toJSONString(inquiryList.get(i)), cy_supplier.class);
                        System.out.println("cySupplier(双):" + cySupplier);
                        mapper.delete_message(cySupplier);
                    }
                }
            }
            return JSONUtil.toJson("0", "", "删除成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "删除失败！", "");
        }
    }
}
