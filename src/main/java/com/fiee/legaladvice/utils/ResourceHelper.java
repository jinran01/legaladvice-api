package com.fiee.legaladvice.utils;



import com.fiee.legaladvice.entity.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fiee
 * @ClassName: ResourceHelper
 * @Date: 2024/3/3
 * @Version: v1.0.0
 **/
public class ResourceHelper {

    public static List<Resource> resourceHelper(List<Resource> list) {
        List<Resource> tree = new ArrayList<>();
        for (Resource item : list) {
            if (item.getParentId() == null){
                tree.add(doHelper(item,list));
            }
        }
        return tree;
    }

    private static Resource doHelper(Resource item, List<Resource> list) {
        item.setChildren(new ArrayList<>());
        for (Resource resource : list) {
            //获取资源id
            Integer rid = item.getId();
            //获取资源的parentId
            Integer parentId = resource.getParentId();
            if (rid.equals(parentId)){
                item.getChildren().add(doHelper(resource,list));
            }
        }
        return item;
    }


}
