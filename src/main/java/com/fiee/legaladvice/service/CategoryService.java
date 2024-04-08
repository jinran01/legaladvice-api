package com.fiee.legaladvice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.dto.CategoryDTO;
import com.fiee.legaladvice.entity.Category;
import com.fiee.legaladvice.vo.CategoryVO;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_category】的数据库操作Service
* @createDate 2024-03-03 23:51:04
*/
public interface CategoryService extends IService<Category> {


    PageResult<CategoryVO> getCategoryList(ConditionVO vo);

    boolean removeBatch(List<Category> categories);

//    List<CategoryDTO> listCategoryDTO();
}
