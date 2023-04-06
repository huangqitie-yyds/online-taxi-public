package com.huangqitie.pojo;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author hqt
 * @since 2023-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DicDistrict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地区编码
     */
    private String addressCode;

    /**
     * 地区名称
     */
    private String addressName;

    /**
     * 父地区编码
     */
    private String parentAddressCode;

    /**
     * 级别
     */
    private Integer level;


}
