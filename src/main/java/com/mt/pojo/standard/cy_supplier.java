package com.mt.pojo.standard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 选择供应商数据
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class cy_supplier {
    private String ID;//ID
    private String suppliername;//供应商名称
    private String suppliercode;//供应商编码
    private String startdate;//开标时间
    private String enddate;//截标时间
    private String type;//框架类型
}
