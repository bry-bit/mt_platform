package com.mt.mapper.manage;

import com.mt.pojo.manage.DownLoad;
import com.mt.pojo.manage.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FileUploadMapper {

    void insert(FileInfo fileInfo);//插入文件上传数据

    List<FileInfo> select1(@Param("pl") Integer pl, @Param("limit") Integer limit);//查找前10条上传文件数据

    List<FileInfo> select2();//查询所有上传文件数据

    List<FileInfo> SelectTableController(String ID); //上传记录

    String SelectUrl(@Param("ID") String ID);//下载文件路径

    void insertdown(DownLoad downLoad);//插入文件下载数据

    List<DownLoad> SelectNumber(@Param("pl") Integer pl, @Param("limit") Integer limit);//查找前10条下载文件数据

    List<DownLoad> SelectAll();//查找所有下载文件数据

}
