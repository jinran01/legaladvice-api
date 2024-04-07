package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.entity.Article;
import com.fiee.legaladvice.entity.ArticleTag;
import com.fiee.legaladvice.entity.Category;
import com.fiee.legaladvice.entity.Tag;
import com.fiee.legaladvice.exception.BizException;
import com.fiee.legaladvice.service.ArticleTagService;
import com.fiee.legaladvice.service.TagService;
import com.fiee.legaladvice.mapper.TagMapper;
import com.fiee.legaladvice.utils.BeanCopyUtils;
import com.fiee.legaladvice.vo.CategoryVO;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import com.fiee.legaladvice.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Fiee
* @description 针对表【tb_tag】的数据库操作Service实现
* @createDate 2023-04-09 21:11:19
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public PageResult getTagList(ConditionVO vo) {
        //查询条数
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(vo.getKeywords()!=null,Tag::getTagName,vo.getKeywords());
        Integer count = Math.toIntExact(this.count(wrapper));
        List<Tag> tagList = baseMapper.getTagList(vo, (vo.getCurrent() - 1) * vo.getSize(), vo.getSize());
        List<TagVO> tagVOList = BeanCopyUtils.copyList(tagList, TagVO.class);
        List<TagVO> collect = tagVOList.stream().map(item -> {
            LambdaQueryWrapper<ArticleTag> articleWrapper = new LambdaQueryWrapper<>();
            articleWrapper.eq(ArticleTag::getTagId,item.getId());
            item.setArticleCount(Math.toIntExact(articleTagService.count(articleWrapper)));
            return item;
        }).collect(Collectors.toList());

        return new PageResult<>(collect,count);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean removeBatch(List<Tag> asList) {
        for (Tag tag:asList) {
            LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ArticleTag::getTagId,tag.getId());
            if (articleTagService.getOne(wrapper) != null){
                throw new BizException("该分类下有文章");
            }
            this.removeById(tag);
        }
        return true;
    }
}




