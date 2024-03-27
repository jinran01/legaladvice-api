package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.annotation.OptLog;
import com.fiee.legaladvice.service.OperationLogService;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fiee.legaladvice.constant.OptTypeConst.REMOVE;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.controller
 * @Date: 2024/3/17
 * @Version: v1.0.0
 **/

@Api(tags = "日志管理")
@RestController
@RequestMapping("/admin")
public class OperationController {
    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation("操作日志列表")
    @GetMapping("/operation/logs")
    public Result getOperations(ConditionVO conditionVO){
        return Result.ok(operationLogService.getOperations(conditionVO));
    }

    @ApiOperation("删除操作日志")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/operation/logs")
    public Result delOperations(@RequestBody List<Integer> ids){
        return Result.ok(operationLogService.delOperations(ids));
    }
}
