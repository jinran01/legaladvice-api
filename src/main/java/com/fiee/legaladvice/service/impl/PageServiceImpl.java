package com.fiee.legaladvice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.entity.Page;
import com.fiee.legaladvice.service.PageService;
import com.fiee.legaladvice.mapper.PageMapper;
import com.fiee.legaladvice.service.RedisService;
import com.fiee.legaladvice.utils.BeanCopyUtils;
import com.fiee.legaladvice.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.fiee.legaladvice.constant.RedisPrefixConst.PAGE_COVER;

/**
* @author Fiee
* @description 针对表【tb_page(页面)】的数据库操作Service实现
* @createDate 2024-04-11 00:00:51
*/
@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, Page>
    implements PageService{

    @Autowired
    private RedisService redisService;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<PageVO> listPages() {
        List<PageVO> pageVOList;
        // 查找缓存信息，不存在则从mysql读取，更新缓存
        Object pageList = redisService.get(PAGE_COVER);
        if (Objects.nonNull(pageList)) {
            pageVOList = JSON.parseObject(pageList.toString(), List.class);
        } else {
            pageVOList = BeanCopyUtils.copyList(baseMapper.selectList(null), PageVO.class);
            redisService.set(PAGE_COVER, JSON.toJSONString(pageVOList));
        }
        return pageVOList;
    }
}




