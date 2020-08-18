package com.mt.service.manage;

import com.mt.pojo.manage.cy_manage_subject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Manage_ThemeService {
    //查询相关创建人下的主题信息
    List<cy_manage_subject> select(String fd_creat_person);

    //主题列表分页
    List<cy_manage_subject> selectPage(@Param("limit") Integer limit
            , @Param("page") Integer page, @Param("fd_creat_person") String fd_creat_person);

    //创建新的主题信息
    void insert(cy_manage_subject manageSubject);

    //修改主题名称
    void update(cy_manage_subject manageSubject);

    //删除主题数据
    void delete(String fd_id);
}
