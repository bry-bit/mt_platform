package com.mt.service.manage;

import com.mt.pojo.manage.DownLoad;
import com.mt.pojo.manage.FileInfo;

import java.util.List;

public interface FileUploadService {

    void insert(FileInfo fileInfo);//插入文件上传数据

    List<FileInfo> select1(int pl, Integer limit);//查找前10条上传文件数据

    List<FileInfo> select2();//查询所有上传文件数据

    String SelectUrl(String ID);//下载文件路径

    void insertdown(DownLoad downLoad);//插入文件下载数据

    List<DownLoad> SelectNumber(int pl, Integer limit);//查找前10条下载文件数据

    List<DownLoad> SelectAll();//查找所有下载文件数据
}
