package com.mt.mapper.authorization;

import com.mt.pojo.authorization.cy_menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sporty_MenusMapper {
    List<cy_menu> Menu_List();
}
