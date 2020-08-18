package com.mt.controller.manage;

import com.alibaba.fastjson.JSONObject;
import com.mt.mapper.manage.Manage_ThemeMapper;
import com.mt.pojo.manage.cy_manage_subject;
import com.mt.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;

/**
 * 管理类模块——主题列表（新增、查询）
 */
@Controller
public class Manage_Theme {
    @Autowired
    private Manage_ThemeMapper service;

    //返回值


    /**
     * 创建新的主题
     *
     * @param data 前端传入的数据值
     * @return 返回成功、失败或异常
     */
    @RequestMapping("newtopic")
    @ResponseBody
    public String newtopic(@RequestBody String data) {
        try {
            System.out.println("获取创建主题数据：" + data);
            //将获取的数据进行对象封装
            cy_manage_subject manage_subject = JSONObject.parseObject(data, cy_manage_subject.class);

            //生产随机ID
            Random random = new Random();
            long id = random.nextLong();
            manage_subject.setFd_id(String.valueOf(id));

            //进行创建插入
            service.insert(manage_subject);
            return JSONUtil.toJson("0", "", "创建成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "创建失败！", "");
        }
    }

    /**
     * 主题列表数据展现（采购）
     *
     * @param fd_creat_person 登录人
     * @param page            第几页
     * @param limit           一页的数量
     * @return 返回整体数据
     */
    @RequestMapping("ListOfTopics")
    @ResponseBody
    public String ListOfTopics(String fd_creat_person, Integer page, Integer limit) {
        System.out.println(fd_creat_person);
        try {
            //获取相关人员下的全部数据
            List<cy_manage_subject> lists = service.select(fd_creat_person);
            //分页
            List<cy_manage_subject> list = service.selectPage(limit, page, fd_creat_person);

            return JSONUtil.toJson("0", list, "创建成功！", lists);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "创建失败！", "");
        }
    }

    /**
     * 修改主题名称
     *
     * @param data 要修改的主题数据
     * @return
     */
    @RequestMapping("UsingThemes")
    @ResponseBody
    public String UsingThemes(@RequestBody String data) {
        try {
            System.out.println("要修改的主题数据：" + data);
            //将获取的数据进行对象封装
            cy_manage_subject manage_subject = JSONObject.parseObject(data, cy_manage_subject.class);
            //进行修改
            service.update(manage_subject);
            return JSONUtil.toJson("0", "", "修改成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "修改失败！", "");
        }
    }

    /**
     * 删除主题
     *
     * @param fd_id 要删除的主题ID
     * @return
     */
    @RequestMapping("DeleteTheTopic")
    @ResponseBody
    public String DeleteTheTopic(String fd_id) {
        try {
            service.delete(fd_id);
            return JSONUtil.toJson("0", "", "删除成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "删除失败！", "");
        }
    }
}
