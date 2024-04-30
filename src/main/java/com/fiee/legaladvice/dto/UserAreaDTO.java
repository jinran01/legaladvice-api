package com.fiee.legaladvice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Fiee
 * @ClassName: UserAreaDTO
 * @Date: 2024/5/1
 * @Version: v1.0.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAreaDTO {
    /**
     * 地区名
     */
    private String city;

    /**
     * 数量
     */
    private Long uv;
}
