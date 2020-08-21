package com.mt.controller.manage;

import com.mt.pojo.manage.DownLoad;
import com.mt.pojo.manage.FileInfo;
import com.mt.service.manage.FileUploadService;
import com.mt.util.JSONUtil;
import com.mt.util.upload.UploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 上传下载文件
 */
@Controller
public class FileUploadController {

    @Resource
    private FileUploadService service;


    /**
     * @param fileN
     * @param subjectid
     * @param ID
     * @param suppliername
     * @param suppliercode
     * @param supplierid
     * @return
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public String postR(@RequestParam MultipartFile[] fileN, String subjectid, String ID, String suppliername, String suppliercode, String supplierid) {
        System.out.println("fileN=" + fileN);
        System.out.println("ID:" + ID);
        if (ID == null) {
            System.out.println("1");
            ID = "测试人员";
        }
        if (subjectid == null) {
            subjectid = "23232";
        }
        String tt = "D:/美腾供应商平台附件管理/" + ID + "/" + subjectid + "/";//D:/下载文件/人员ID/主题ID/
        System.out.println("tt=" + tt);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd HHmmss");
        System.out.println("文件数量=" + fileN.length);
        for (int i = 0; i < fileN.length; i++) {
            System.out.println("进入解析文件代码");
            String fileName = fileN[i].getOriginalFilename();//文件名
            System.out.println("获取文件名称" + fileName);
            //获取去除后缀名
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            System.out.println("fileName文件=" + fileName);
            //对文件进行解析 获取后缀名
            String fileType = fileN[i].getOriginalFilename().substring(fileN[i].getOriginalFilename().lastIndexOf(".") + 1);
            System.out.println("fileType=" + fileType);
            String name = fileName + df1.format(new Date()) + "." + fileType;//文件名称+yyyyMMdd HHmmss.后缀类型
            UploadUtils.approvalFile(fileN[i], tt, name);//上传文件
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fileID = getUUID();//获取随机ID
                FileInfo fileInfo = new FileInfo();//文件信息实体类
                fileInfo.setFd_id(fileID);//文件ID
                fileInfo.setFd_file_name(name);//文件名称
                fileInfo.setFd_subject_id(subjectid);//主题ID
                fileInfo.setFd_time(df.format(new Date()));//上传时间
                fileInfo.setFd_url(ID + "/" + subjectid + "/" + name);//文件URL:人员ID+主题ID+文件名称
                fileInfo.setFd_creat_person(ID);//上传人
                fileInfo.setSupplierid(supplierid);
                fileInfo.setSuppliername(suppliername);
                fileInfo.setSuppliercode(suppliercode);
                service.insert(fileInfo);
                System.out.println("成功");
            } catch (Exception e) {
                e.printStackTrace();
                return JSONUtil.toJson("1", "", "上传" + fileName + "文件失败！", "");//返回前端上传文件状态
            }
        }
        return JSONUtil.toJson("0", "", "上传成功", "");//返回上传文件成功状态
    }

    /**
     * 上传记录数据显示
     *
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("DataShow")
    @ResponseBody
    public String DataShow(Integer page, Integer limit) {
        try {
            System.out.println(page);
            System.out.println(limit);
            int pl = page * limit - limit;
            int ll = page * limit;
            System.out.println("pl=============" + pl);
            List<FileInfo> list = service.select2();//显示全部的上传记录
            List<FileInfo> list1 = service.select1(pl, ll);//分页显示上传记录
            return JSONUtil.toJson("0", list1, "数据呈现成功！", list.size());//返回前端数据显示成功
        } catch (Exception e) {
            return JSONUtil.toJson("1", "", "数据呈现失败！", "");//返回前端数据显示失败
        }
    }

    /**
     * 生成32位编码 // import java.util.UUID;
     *
     * @return string
     * 1.Java中UUID生成的时候，默认是小写的，比如这个（ff3c1234-4108-4679-abdd-fe45dd713780）
     * 2.Java中生成UUID后，可以通过toLowerCase()转换成小写。通过.toUpperCase()转换成大写。
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "").toUpperCase();
        return uuid;
    }


    /**
     * 下载文件
     *
     * @param request
     * @param response
     * @param userid   人员ID
     * @param fileid   文件ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/downloadFile")
    @ResponseBody
    private String downloadFile(HttpServletRequest request, HttpServletResponse response, String userid, String fileid) throws Exception {
        System.out.println("userid:" + userid + ",fileid:" + fileid);
        if (userid == null) {
            userid = "测试人员";
        }
        //被下载的文件在服务器中的路径
        String path = "D:/美腾供应商平台附件管理/";
        if (fileid == null) {
            fileid = "CFFB22F35E604C759571BCCFA428FB19";
        }
        //被下载文件的名称
        String fileName = service.SelectUrl(fileid);//根据文件ID查询该文件上传URL
        String userAgent = request.getHeader("User-Agent");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//获取当前系统时间
        File file = new File(path + fileName);//拼接下载文件的正确上传路径
        //避免文件名称出现乱码，区分浏览器
        // 针对IE或者以IE为内核的浏览器：
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }

        if (file.exists()) {
            // 设置强制下载不打开
            /*response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);*/
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
            //response.setContentType("multipart/form-data");
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    outputStream.flush();
                    i = bis.read(buffer);
                }
                String fileID = getUUID();
                DownLoad download = new DownLoad();
                download.setFd_id(fileID);//下载记录ID
                download.setFd_download_person(userid);//下载人
                download.setFd_fileid(fileid);//下载文件ID
                download.setFd_download_date(df.format(new Date()));//下载时间
                service.insertdown(download);
                System.out.println("成功");
                System.out.println("下载成功 次数加一");
                return "下载成功";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "下载失败";
    }


    /**
     * 下载记录数据显示
     *
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("downShow")
    @ResponseBody
    public String downShow(Integer page, Integer limit) {
        try {
            System.out.println(page);
            System.out.println(limit);
            int pl = page * limit - limit;
            int ll = page * limit;
            System.out.println("pl=============" + pl);
            List<DownLoad> list = service.SelectAll();
            List<DownLoad> list1 = service.SelectNumber(pl, ll);
            return JSONUtil.toJson("0", list1, "数据呈现成功！", list.size());
        } catch (Exception e) {
            return JSONUtil.toJson("1", "", "数据呈现失败！", "");
        }
    }

}



