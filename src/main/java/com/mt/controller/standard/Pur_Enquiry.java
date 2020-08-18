package com.mt.controller.standard;

import com.alibaba.fastjson.JSONObject;
import com.mt.mapper.standard.Pur_EnquiryMapper;
import com.mt.pojo.standard.cy_inquiry;
import com.mt.pojo.standard.cy_inquiry_detailed;
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
public class Pur_Enquiry {
    @Autowired
    private Pur_EnquiryMapper mapper;

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
            List<cy_inquiry> inquiryList = mapper.Main_sheet(fd_applicant);

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
}