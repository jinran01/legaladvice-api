package com.fiee.legaladvice.service;

import com.fiee.legaladvice.dto.TagDTO;
import com.fiee.legaladvice.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_tag】的数据库操作Service
* @createDate 2023-04-09 21:11:19
*/
public interface TagService extends IService<Tag> {

    PageResult getTagList(ConditionVO vo);

    boolean removeBatch(List<Tag> asList);

    PageResult<TagDTO> listTags();
}
