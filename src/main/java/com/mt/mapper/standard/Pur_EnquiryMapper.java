package com.mt.mapper.standard;

import com.mt.pojo.standard.cy_inquiry;
import com.mt.pojo.standard.cy_inquiry_detailed;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Pur_EnquiryMapper {
    //获取U8请购单到询价表中，并展现相应的供应商数据
    List<cy_inquiry> Main_sheet(String cPersonName);

    //查询当天最大的编号尾数
    String max_orderno();

    //该询价单是否由子表询价
    void update(@Param("fd_edit") String fd_edit, @Param("fd_id") String fd_id);

    void Main_inquiry(cy_inquiry inquiry);

    void Person_inquiry(cy_inquiry inquiry);

    List<cy_inquiry_detailed> Person_inqdata(String fd_parent_id);
}
