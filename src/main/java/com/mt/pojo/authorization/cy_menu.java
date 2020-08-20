package com.mt.pojo.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 菜单表
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_menu {
    // 菜单id
    private String id;
    // 父菜单id
    private String parentId;
    // 菜单名称
    private String name;
    // 菜单url
    private String url;
    // 子菜单
    private List<cy_menu> children;
}
