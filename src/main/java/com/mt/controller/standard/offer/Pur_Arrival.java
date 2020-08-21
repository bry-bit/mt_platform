package com.mt.controller.standard.offer;

import com.mt.mapper.standard.offer.Pur_OrderMapper;
import com.mt.pojo.manage.DownLoad;
import com.mt.pojo.manage.FileInfo;
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
import java.util.Map;
import java.util.UUID;

@Controller
public class Pur_Arrival {
    @Resource
    Pur_OrderMapper pur_orderMapper;

    /**
     *
     * @param fileN 文件
     * @param map  json 包含数据ID 用于更新URL
     * @return
     */
    @PostMapping(value = "/Arrival")
    @ResponseBody

   /* //map接收
    @PostMapping(path = "/demo1")
    public void demo1(@RequestBody Map<String, String> person) {
        System.out.println(person.get("name"));
    }

    //或者是实体对象接收
    @PostMapping(path = "/demo1")
    public void demo1(@RequestBody Person person) {
        System.out.println(person.toString());*/

        public Map<String,String> Arrival(@RequestParam MultipartFile[] fileN,  Map<String,String> map) {
        System.out.println("fileN=" + fileN);
        String tt = "D:/美腾供应商平台附件管理/" + map.get("comment")+ "/" + map.get("fd_id") + "/";//D:/美腾供应商平台附件管理/资质证明/数据ID/
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
                map.put("fd_url",map.get("comment")+ "/" + map.get("fd_id") + "/" + name);//文件URL:目录/数据ID/文件名称
                System.out.println("成功");
            } catch (Exception e) {
                e.printStackTrace();
                //return JSONUtil.toJson("1", "", "上传" + fileName + "文件失败！", "");//返回前端上传文件状态
                map.put("msg",JSONUtil.toJson("1", "", "上传" + fileName + "文件失败！", ""));
                return map;
            }
        }
        //return JSONUtil.toJson("0", "", "上传成功", "");//返回上传文件成功状态
        return map;
    }

    /**
     * 下载文件
     *
     * @param request
     * @param response
     * @param fd_id   数据ID
     * @param type   到货凭证：1  资质证明：2
     * @return
     * @throws Exception
     */
    @RequestMapping("/downArrival")
    @ResponseBody
    private String downArrival(HttpServletRequest request, HttpServletResponse response, String fd_id,Integer type) throws Exception {

        //被下载的文件在服务器中的路径
        String path = "D:/美腾供应商平台附件管理/";
        String fileName ="";
        //被下载文件的名称
        if(type==1) {
             //获取上传到供应商发货子表的到货凭证路径
             fileName = pur_orderMapper.select_url(fd_id);//根据文件ID查询该文件上传URL
        }else if(type==2){
            //获取供应商资质证明文件路径
        }
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
                System.out.println("下载成功附件");
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

}
