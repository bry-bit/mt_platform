package com.mt.controller.frame;

import com.mt.mapper.frame.FrameInquiryMapper;
import com.mt.pojo.frame.cy_kinquiry;
import com.mt.pojo.frame.cy_kinquiry_details;
import com.mt.util.IDUtil;
import com.mt.util.JSONUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ExcelFileController {
    @Resource
    private FrameInquiryMapper frameInquiryMapper;
    JSONUtil jsonUtil = new JSONUtil();


    /**
     * @param file 文件
     * @param ID   用户ID
     * @return
     */
    @RequestMapping("FileUpload")
    @ResponseBody
    public String FileUpload(@RequestParam("file") MultipartFile file, String ID) {
        if (ID == null) {
            ID = "123456";
        }
        System.out.println(ID);
        String tt = "D:/美腾供应商平台框架类询价单Excel/" + ID + "/";
        String fff = tt + file.getOriginalFilename();
        System.out.println(fff);
        File dir = new File(tt);
        if (!dir.exists()) {//如果文件夹不存在
            dir.mkdirs();//创建文件夹
        }
        File file1 = new File(fff);
        if (!file1.exists()) {//如果文件夹不存在
            //file1.mkdir();
        }
        try {//异常处理
            //如果file文件夹下没有test.txt就会创建该文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(fff));
            bw.close();//一定要关闭文件
        } catch (IOException e) {
            return jsonUtil.toJson("2", "", "该文件已存在！", "");
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String inqID = IDUtil.getUUID();
            cy_kinquiry cykinquiry = new cy_kinquiry();//框架类询价的主表
            cykinquiry.setFd_id(inqID);//主表ID
            cykinquiry.setFd_creat_person(ID);//创建人ID
            cykinquiry.setFd_creat_time(format.format(new Date()));//创建时间
            cykinquiry.setFd_filename(file.getOriginalFilename());//上传文件名称
            cykinquiry.setFd_url(fff.substring(2));//上传文件路径
            System.out.println("inquiry主表数据=" + cykinquiry);
            frameInquiryMapper.excel1(cykinquiry);//生成询价单主表数据


            //对Excel文件进行解析（分为xls/xlsx）
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            //通过不同后缀名进行解析
            Workbook workbook = null;
            if (fileType.equals("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (fileType.equals("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                return jsonUtil.toJson("3", "", "上传Excel文件类型错误！", "");
            }
            //对Excel表格解析，存入数据组中
            Sheet sheet = workbook.getSheetAt(0);

            List<List<String>> listOut = new ArrayList<>();

            CellReference cellReference = new CellReference("A4");
            boolean flag = false;
            for (int i = cellReference.getRow(); i <= sheet.getLastRowNum(); ) {
                Row r = sheet.getRow(i);
                if (r == null) {
                    // 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                    continue;
                }
                flag = false;
                for (Cell c : r) {
                    if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    i++;
                    continue;
                } else {//如果是空白行（即可能没有数据，但是有一定格式）
                    if (i == sheet.getLastRowNum())//如果到了最后一行，直接将那一行remove掉
                        sheet.removeRow(r);
                    else//如果还没到最后一行，则数据往上移一行
                        sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                }
            }
            System.out.println("总行数：" + (sheet.getLastRowNum() + 1));
            for (Row row : sheet) {
                List<String> listIn = new ArrayList<>(); //一个sheet一行
                for (Cell cell : row) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    listIn.add(cell.getStringCellValue());
                }
                listOut.add(listIn);
            }
            for (int i = 2; i < sheet.getLastRowNum() + 1; i++) {
                cy_kinquiry_details cykinquirydetails = new cy_kinquiry_details();
                if (listOut.get(i).get(0).isEmpty()) {
                    System.out.println(i + "行数据为空单元格0");
                } else {
                    cykinquirydetails.setFd_order(listOut.get(i).get(0));//序号
                }
                if (listOut.get(i).get(1).isEmpty()) {
                    System.out.println(i + "行数据为空单元格1");
                } else {
                    cykinquirydetails.setFd_inventory_no(listOut.get(i).get(1));//物料编号
                }
                if (listOut.get(i).get(2).isEmpty()) {
                    System.out.println(i + "行数据为空单元格2");
                } else {
                    cykinquirydetails.setFd_inventory_name(listOut.get(i).get(2));//设备名称
                }
                if (listOut.get(i).get(3).isEmpty()) {
                    System.out.println(i + "行数据为空单元格3");
                } else {
                    cykinquirydetails.setFd_brand(listOut.get(i).get(3));//品牌
                }
                if (listOut.get(i).get(4).isEmpty()) {
                    System.out.println(i + "行数据为空单元格4");
                } else {
                    cykinquirydetails.setFd_model(listOut.get(i).get(4));//型号
                }
                if (listOut.get(i).get(5).isEmpty()) {
                    System.out.println(i + "行数据为空单元格5");
                } else {
                    cykinquirydetails.setFd_parameter(listOut.get(i).get(5));//参数
                }
                if (listOut.get(i).get(6).isEmpty()) {
                    System.out.println(i + "行数据为空单元格6");
                } else {
                    cykinquirydetails.setFd_unit(listOut.get(i).get(6));//单位
                }
                if (listOut.get(i).get(7).isEmpty()) {
                    System.out.println(i + "行数据为空单元格7");
                    cykinquirydetails.setFd_price("0");
                } else {
                    cykinquirydetails.setFd_price(listOut.get(i).get(7));//单价
                }
                if (listOut.get(i).get(8).isEmpty()) {
                    System.out.println(i + "行数据为空单元格8");
                } else {
                    cykinquirydetails.setFd_cycle(listOut.get(i).get(8));//供货周期
                }
                if (listOut.get(i).get(9).isEmpty()) {
                    System.out.println(i + "行数据为空单元格9");
                } else {
                    cykinquirydetails.setFd_methods(listOut.get(i).get(9));//付款方式
                }
                if (listOut.get(i).get(10).isEmpty()) {
                    System.out.println(i + "行数据为空单元格10");
                } else {
                    cykinquirydetails.setFd_technical_para(listOut.get(i).get(10));//报价技术参数
                }
                if (listOut.get(i).get(11).isEmpty()) {
                    System.out.println(i + "行数据为空单元格11");
                } else {
                    cykinquirydetails.setFd_validity(listOut.get(i).get(11));//报价有效期
                }
                if (listOut.get(i).get(12).isEmpty()) {
                    System.out.println(i + "行数据为空单元格12");
                } else {
                    cykinquirydetails.setFd_remarks(listOut.get(i).get(12));//备注
                }
                cykinquirydetails.setFd_id(IDUtil.getUUID());//子表随机ID
                cykinquirydetails.setFd_parent_id(inqID);//关联主表ID
                System.out.println("inquiryDetails数据=" + cykinquirydetails);
                frameInquiryMapper.excel2(cykinquirydetails);//将上传文件导入中间表子表
            }

            System.out.println("所有数据=" + listOut);


            return jsonUtil.toJson("0", file.getOriginalFilename(), "上传成功", "");
        } catch (Exception e) {
            e.printStackTrace();
            return jsonUtil.toJson("1", "", "上传失败！", "");
        }
    }

}
