package com.fiee.legaladvice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.entity.Resource;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Fiee
* @description 针对表【tb_resource】的数据库操作Mapper
* @createDate 2024-03-02 20:01:43
* @Entity com.fiee.legaladvice.Resource
*/
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

}




