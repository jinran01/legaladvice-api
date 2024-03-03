package com.fiee.legaladvice.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Author: Fiee
 * @ClassName: MyMetaObjectHandle 公共字段自动填充
 * @Date: 2024/3/2
 * @Version: v1.0.0
 **/

@Component
@Slf4j
public class MyMetaObjectHandle implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,
                "createTime",
                LocalDateTime.class,
                LocalDateTime.now(ZoneId.of("Asia/Shanghai")));

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,
                "createTime",
                LocalDateTime.class,
                LocalDateTime.now(ZoneId.of("Asia/Shanghai")));

        this.strictInsertFill(metaObject,
                "updateTime",
                LocalDateTime.class,
                LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
    }
}
