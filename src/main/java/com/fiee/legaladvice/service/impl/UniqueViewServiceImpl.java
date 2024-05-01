package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.entity.UniqueView;
import com.fiee.legaladvice.mapper.UniqueViewMapper;
import com.fiee.legaladvice.service.UniqueViewService;
import org.springframework.stereotype.Service;

/**
 * @Author: Fiee
 * @ClassName: UniqueViewServiceImpl
 * @Date: 2024/5/1
 * @Version: v1.0.0
 **/
@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewMapper, UniqueView> implements UniqueViewService {
}
