package com.mt.mapper.standard.enqury;

import com.mt.pojo.standard.cy_supplier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Pur_SelectSupMapper {
    //查询U8供应商档案表
    List<cy_supplier> Chose_sup();

    //进行供应商档案数据的分页操作
    List<cy_supplier> Chose_sup_page(@Param("page") Integer page, @Param("limit") Integer limit);

    //供应商平台中新增供应商档案
    void addsupp(cy_supplier supplier);

    //根据流程ID查询供应商平台中的供应商档案表数据
    List<cy_supplier> select_supp(@Param("ID") String ID, @Param("suppliername") String suppliername
            , @Param("type") String type);

    //修改供应商的开标、结标时间
    void update_time(cy_supplier supplier);

    //删除已选择的供应商
    void delete_message(cy_supplier supplier);
}
