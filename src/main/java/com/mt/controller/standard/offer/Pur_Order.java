package com.mt.controller.standard.offer;

import com.mt.util.RandomUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.SQLOutput;

@Controller
public class Pur_Order {
    @Resource
    RandomUtil randomUtil;

    @RequestMapping("insertdata")
    @ResponseBody
    public String InsertData() {
        //调用方法类获取报价单编号
        String aaa = randomUtil.getNewAutoNum();
        System.out.println("aaa=" + aaa);
        return null;
    }
}
