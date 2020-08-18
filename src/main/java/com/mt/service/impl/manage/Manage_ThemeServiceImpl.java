package com.mt.service.impl.manage;

import com.mt.mapper.manage.Manage_ThemeMapper;
import com.mt.pojo.manage.cy_manage_subject;
import com.mt.service.manage.Manage_ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Manage_ThemeServiceImpl implements Manage_ThemeService {
    @Autowired
    private Manage_ThemeMapper mapper;

    @Override
    public List<cy_manage_subject> select(String fd_creat_person) {
        List<cy_manage_subject> lists = mapper.select(fd_creat_person);

        System.out.println("lists  111=" + lists.toString());
        return lists;
    }

    @Override
    public List<cy_manage_subject> selectPage(Integer limit, Integer page, String fd_creat_person) {
        List<cy_manage_subject> list = mapper.selectPage(limit, page, fd_creat_person);
        return list;
    }

    @Override
    public void insert(cy_manage_subject manageSubject) {
        mapper.insert(manageSubject);
    }

    @Override
    public void update(cy_manage_subject manageSubject) {
        mapper.update(manageSubject);
    }

    @Override
    public void delete(String fd_id) {
        mapper.delete(fd_id);
    }
}
