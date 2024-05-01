package com.fiee.legaladvice.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: Fiee
 * @ClassName: IncreateData
 * @Date: 2024/4/8
 * @Version: v1.0.0
 **/
@Data
@Builder
@TableName("tb_increate_data")
public class IncreateData implements Serializable {

    /**
     * 时间
     */
    private LocalDateTime dateTime;

    /**
     *访问量
     */
    private Integer incPv;

    /**
     *留言量
     */
    private Integer incMsg;

    /**
     *文章量
     */
    private Integer incArt;

    /**
     *用户量
     */
    private Integer incUser;

}
