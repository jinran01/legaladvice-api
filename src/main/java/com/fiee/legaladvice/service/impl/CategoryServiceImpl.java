package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.dto.CategoryDTO;
import com.fiee.legaladvice.entity.Article;
import com.fiee.legaladvice.entity.Category;
import com.fiee.legaladvice.exception.BizException;
import com.fiee.legaladvice.mapper.ArticleMapper;
import com.fiee.legaladvice.service.CategoryService;
import com.fiee.legaladvice.mapper.CategoryMapper;
import com.fiee.legaladvice.utils.BeanCopyUtils;
import com.fiee.legaladvice.vo.CategoryVO;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fiee
 * @description 针对表【tb_category】的数据库操作Service实现
 * @createDate 2023-05-14 19:11:41
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService{


    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public PageResult<CategoryVO> getCategoryList(ConditionVO vo) {
        //查询条数
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(vo.getKeywords()!=null,Category::getCategoryName,vo.getKeywords());
        Integer count = Math.toIntExact(this.count(wrapper));
        List<Category> categoryList = baseMapper.getCategoryList(vo, (vo.getCurrent() - 1) * vo.getSize(), vo.getSize());
        List<CategoryVO> categoryVOList = BeanCopyUtils.copyList(categoryList, CategoryVO.class);
        List<CategoryVO> collect = categoryVOList.stream().map(item -> {
            LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
            articleWrapper.eq(Article::getCategoryId,item.getId());
            item.setArticleCount(Math.toIntExact(articleMapper.selectCount(articleWrapper)));
            return item;
        }).collect(Collectors.toList());
        return new PageResult<>(collect,count);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean removeBatch(Long[] categories) {
        for (Long category:categories) {
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Article::getCategoryId,category);
            if (articleMapper.selectOne(wrapper) != null){
                throw new BizException("该分类下有文章");
            }
            this.removeById(category);
        }
        return true;
    }

}




