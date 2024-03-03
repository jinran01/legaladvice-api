package com.fiee.legaladvice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Fiee
* @description 针对表【tb_category】的数据库操作Mapper
* @createDate 2024-03-03 23:51:04
* @Entity com.fiee.legaladvice.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




