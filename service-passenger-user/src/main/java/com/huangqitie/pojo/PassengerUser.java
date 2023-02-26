package com.huangqitie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author hqt
 * @since 2023-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PassengerUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String passengerPhone;

    private String passengerName;

    private Byte passengerGender;

    private Byte state;


}
