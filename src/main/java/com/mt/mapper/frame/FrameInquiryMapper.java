package com.mt.mapper.frame;

import com.mt.pojo.frame.cy_kinquiry;
import com.mt.pojo.frame.cy_kinquiry_details;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
/**
 * 框架类导入Excel插入数据库操作
 */
public interface FrameInquiryMapper {
    void excel1(cy_kinquiry cykinquiry);//Excel导入后生成框架类询价单主表

    void excel2(cy_kinquiry_details cykinquirydetails);//生成框架类询价单子表数据

}
