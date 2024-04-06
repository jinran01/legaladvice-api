package com.fiee.legaladvice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.entity.Category;
import com.fiee.legaladvice.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_category】的数据库操作Mapper
* @createDate 2024-03-03 23:51:04
* @Entity com.fiee.legaladvice.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> getCategoryList(@Param("vo") ConditionVO vo, Long current, Long size);

}




