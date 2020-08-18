package com.mt.mapper.standard;

import com.mt.pojo.standard.cy_inquiry;
import com.mt.pojo.standard.cy_inquiry_detailed;
import com.mt.pojo.standard.cy_order;
import com.mt.pojo.standard.cy_order_detailed;
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

    //根据询价单主表ID更新标准流程询价单主表数据
    void Main_inquiry(cy_inquiry inquiry);

    //根据询价单子表ID更新标准流程询价单子表数据
    void Person_inquiry(cy_inquiry inquiry);

    //根据询价单主表ID查询询价单子表数据
    List<cy_inquiry_detailed> Person_inqdata(String fd_parent_id);

    //新增报价单
    void Main_order(cy_order cyOrder);

    //更新报价单主表数据
    void order_update(cy_order cyOrder);

    //查询报价单主表数据
    List<cy_order> Select_order(@Param("fd_creat_person") String fd_creat_person
            , @Param("fd_supplier_name") String fd_supplier_name, @Param("fd_quotation_tatus") String fd_quotation_tatus);

    //插入报价单子表数据
    void Person_order(cy_order_detailed orderDetailed);

    //根据报价单子表ID更新数据
    void order_sonUpdate(cy_order_detailed orderDetailed);

    //根据报价单主表ID查询报价单子表数据
    List<cy_order_detailed> Select_orderson(String fd_parent_id);
}
