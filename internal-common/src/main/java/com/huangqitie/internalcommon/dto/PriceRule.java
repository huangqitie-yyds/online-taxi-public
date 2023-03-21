package com.huangqitie.internalcommon.dto;

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
public class PriceRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 车辆类型
     */
    private String vehicleType;

    /**
     * 起步价
     */
    private Double startFare;

    private Integer startMile;

    private Double unitPricePerMile;

    private Double unitPricePerMinute;

    private Integer fareVersion;

    private String fareType;


}
