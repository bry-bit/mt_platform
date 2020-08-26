package com.mt.mapper.standard;

import com.mt.pojo.standard.cy_supplier_deliver;
import com.mt.pojo.standard.cy_supplier_deliver_details;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Pur_PriceMapper {
    //生成供应商发货单主表
    void Insert_deliver(cy_supplier_deliver cySupplierDeliver);

    //生成供应商发货子表数据
    void insert_detail(cy_supplier_deliver_details deliverDetails);

    //根据采购员、供应商ID划分显示数据
    List<cy_supplier_deliver> select_deliver(@Param("fd_creat_person") String fd_creat_person
            , @Param("fd_supplier_name") String fd_supplier_name);

    //根据主表ID查询供应商发货子表数据
    List<cy_supplier_deliver_details> select_detail(String fd_parent_id);

    //查询主表数据
    List<cy_supplier_deliver> find_detail(String fd_id);

    void update_number(@Param("fd_shipment_qty") String fd_shipment_qty
            , @Param("fd_id") String fd_id);
}
