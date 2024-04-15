package com.fiee.legaladvice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.entity.Page;
import com.fiee.legaladvice.vo.PageVO;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_page(页面)】的数据库操作Service
* @createDate 2024-04-11 00:00:51
*/
public interface PageService extends IService<Page> {

    List<PageVO> listPages();
}
