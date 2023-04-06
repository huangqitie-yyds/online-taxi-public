package com.huangqitie.internalcommon.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author 黄启贴
 * @since 2023-04-02
 */
@Data
public class DriverUserWorkStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long driverId;

    /**
     * 收车：0；出车：1，暂停：2
     */
    private Integer workStatus;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

}
