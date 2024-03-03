package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.entity.Category;
import com.fiee.legaladvice.service.CategoryService;
import com.fiee.legaladvice.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author Fiee
* @description 针对表【tb_category】的数据库操作Service实现
* @createDate 2024-03-03 23:51:04
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




