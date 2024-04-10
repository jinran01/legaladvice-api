package com.fiee.legaladvice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.entity.Page;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Fiee
* @description 针对表【tb_page(页面)】的数据库操作Mapper
* @createDate 2024-04-11 00:00:51
* @Entity com.fiee.legaladvice.Page
*/
@Mapper
public interface PageMapper extends BaseMapper<Page> {

}




