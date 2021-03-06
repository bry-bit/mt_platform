package com.mt.mapper.standard.offer;

import com.mt.pojo.standard.cy_order;
import com.mt.pojo.standard.cy_order_detailed;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Pur_OrderMapper {
    //查询报价单主表数据
    List<cy_order> Select_order(@Param("fd_order_person") String fd_order_person
            , @Param("fd_supplier_name") String fd_supplier_name
            , @Param("fd_quotation_tatus") String fd_quotation_tatus);

    //根据报价单主表ID查询报价单子表数据
    List<cy_order_detailed> Select_orderson(@Param("fd_parent_id") String fd_parent_id
            , @Param("fd_order_person") String fd_order_person
            , @Param("fd_supplier_name") String fd_supplier_name
            , @Param("fd_quotation_tatus") String fd_quotation_tatus);

    //根据报价单子表ID更新数据
    void order_sonUpdate(cy_order_detailed orderDetailed);

    //根据数据ID查询到货凭证URL
    String select_url(String fd_id);

    //采购员报价审核，进行中标/退回选择的数据列表
    List<cy_order_detailed> audit(String fd_order_person);

    //查询U8供应商档案表的名称
    String SelSup_name(String cVenCode);

    //中标数据生成后，其他数据均为未中标
    void lose_a_bid(cy_order_detailed detailed);
}
