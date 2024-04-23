package com.fiee.legaladvice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.entity.LawyerAuth;
import com.fiee.legaladvice.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Fiee
 * @ClassName: LawyerAuthMapper
 * @Date: 2024/4/23
 * @Version: v1.0.0
 **/

@Mapper
public interface LawyerAuthMapper extends BaseMapper<LawyerAuth> {

    List<LawyerAuth> getLawyerList(@Param("vo") ConditionVO vo, @Param("current") Long current, @Param("size") Long size);
}
