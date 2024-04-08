package com.fiee.legaladvice.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Fiee
 * @ClassName: IncreateDataDTO
 * @Date: 2024/4/8
 * @Version: v1.0.0
 **/
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncreateDataDTO {



    /**
     * 一周访问量
     */
    private List<Integer> viewWeek;

    /**
     * 一周文章数
     */
    private List<Integer> articleWeek;

    /**
     * 一周留言量
     */
    private List<Integer> messageWeek;

    /**
     * 一周用户数
     */
    private List<Integer> userWeek;

}
