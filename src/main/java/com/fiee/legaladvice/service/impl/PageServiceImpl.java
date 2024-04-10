package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.entity.Page;
import com.fiee.legaladvice.service.PageService;
import com.fiee.legaladvice.mapper.PageMapper;
import org.springframework.stereotype.Service;

/**
* @author Fiee
* @description 针对表【tb_page(页面)】的数据库操作Service实现
* @createDate 2024-04-11 00:00:51
*/
@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, Page>
    implements PageService{

}




