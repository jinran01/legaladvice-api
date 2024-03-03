package com.fiee.legaladvice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.entity.Resource;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_resource】的数据库操作Service
* @createDate 2024-03-02 20:01:43
*/
public interface ResourceService extends IService<Resource> {
    //获取资源列表
    List<Resource> getResourceList(String resourceName);

    //更新资源列表
    boolean updateResource(Resource resource);

    boolean delResource(Integer id);

}