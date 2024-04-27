package com.fiee.legaladvice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Fiee
 * @ClassName: HistoryRecord
 * @Date: 2024/4/27
 * @Version: v1.0.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryRecordDTO {
    /**
     *发送方ID
     */
    private Integer userId;
    /**
     * 接收方ID
     */
    private Integer toUserId;
}
