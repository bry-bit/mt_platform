package com.mt.controller.manage;

import com.mt.mapper.manage.FileUploadMapper;
import com.mt.pojo.manage.DownLoad;
import com.mt.pojo.manage.FileInfo;
import com.mt.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 上传下载列表
 */
@Controller
public class TabController {
    @Autowired
    private FileUploadMapper fileUploadMapper;



    /**
     * 上传文件记录
     *
     * @param ID 上传人ID
     * @return
     */
    @RequestMapping("tt")
    @ResponseBody
    public String tt(String ID) {
        System.out.println(ID);
        try {
            List<FileInfo> fileInfo = fileUploadMapper.SelectTableController(ID);
            System.out.println(fileInfo);
            return JSONUtil.toJson("0", fileInfo, "获取数据成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "获取数据失败！", "");
        }

    }


    /**
     * 下载文件记录
     *
     * @return
     */
    @RequestMapping("down")
    @ResponseBody
    public String down() {
        try {
            List<DownLoad> downLoad = fileUploadMapper.SelectAll();
            System.out.println(downLoad);
            return JSONUtil.toJson("0", downLoad, "", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "", "");
        }
    }

}
