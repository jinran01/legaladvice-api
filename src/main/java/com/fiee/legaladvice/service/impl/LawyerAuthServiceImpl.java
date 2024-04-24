package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.entity.LawyerAuth;
import com.fiee.legaladvice.entity.UserRole;
import com.fiee.legaladvice.mapper.LawyerAuthMapper;
import com.fiee.legaladvice.service.LawyerAuthService;
import com.fiee.legaladvice.service.UserRoleService;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Fiee
 * @ClassName: LawyerAuthServiceImpl
 * @Date: 2024/4/23
 * @Version: v1.0.0
 **/
@Service
public class LawyerAuthServiceImpl extends ServiceImpl<LawyerAuthMapper, LawyerAuth>
        implements LawyerAuthService {
    @Autowired
    private UserRoleService userRoleService;
    @Override
    public PageResult<LawyerAuth> getLawyerList(ConditionVO vo) {
        List<LawyerAuth> list = baseMapper.getLawyerList(vo, (vo.getCurrent() - 1) * vo.getSize(), vo.getSize());
        Integer count = Math.toIntExact(this.count());
        return new PageResult<>(list,count);
    }

    /**
     * 律师认证审核
     * @param lawyerAuth
     */
    @Override
    public boolean updateLawyerAuth(LawyerAuth lawyerAuth) {
        boolean flag = false;
        //未通过
        if (lawyerAuth.getStatus() == 3){
            this.saveOrUpdate(lawyerAuth);
        }else {  //通过
            //更新状态
            this.saveOrUpdate(lawyerAuth);
            // 更新用户角色
            UserRole userRole = UserRole.builder()
                    .userId(lawyerAuth.getUserAuthId())
                    .roleId(2)
                    .build();
            userRoleService.save(userRole);
            flag = true;
        }
        return flag;
    }
}
