package com.mt.service.impl.manage;

import com.mt.mapper.manage.FileUploadMapper;
import com.mt.pojo.manage.DownLoad;
import com.mt.pojo.manage.FileInfo;
import com.mt.service.manage.FileUploadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Resource
    FileUploadMapper fileUploadMapper;

    @Override
    public void insert(FileInfo fileInfo) {
        fileUploadMapper.insert(fileInfo);
    }

    @Override
    public List<FileInfo> select1(int pl, Integer limit) {
        List<FileInfo> list1 = fileUploadMapper.select1(pl, limit);
        return list1;
    }

    @Override
    public List<FileInfo> select2() {
        List<FileInfo> list = fileUploadMapper.select2();
        return list;
    }

    @Override
    public String SelectUrl(String ID) {
        return fileUploadMapper.SelectUrl(ID);
    }

    @Override
    public void insertdown(DownLoad downLoad) {

        fileUploadMapper.insertdown(downLoad);
    }

    @Override
    public List<DownLoad> SelectNumber(int pl, Integer limit) {
        List<DownLoad> list3 = fileUploadMapper.SelectNumber(pl, limit);
        return list3;
    }

    @Override
    public List<DownLoad> SelectAll() {
        List<DownLoad> list4 = fileUploadMapper.SelectAll();
        return list4;
    }

}
