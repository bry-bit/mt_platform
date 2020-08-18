package com.mt.mapper.manage;

import com.mt.pojo.manage.cy_manage_subject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理类模块——主题列表（新增、查询）
 * <p>
 * 管理类调用数据库方法
 */
@Repository
public interface Manage_ThemeMapper {
    //查询相关创建人下的主题信息
    List<cy_manage_subject> select(@Param("fd_creat_person") String fd_creat_person);

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
