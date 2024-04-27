package com.fiee.legaladvice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.dto.ConsultUserDTO;
import com.fiee.legaladvice.dto.LawyerAuthDTO;
import com.fiee.legaladvice.entity.ChatRecord;
import com.fiee.legaladvice.entity.LawyerAuth;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;

import java.util.List;

/**
 * @Author: Fiee
 * @ClassName: LawyerAuthService
 * @Date: 2024/4/23
 * @Version: v1.0.0
 **/
public interface LawyerAuthService extends IService<LawyerAuth> {
    PageResult<LawyerAuth> getLawyerList(ConditionVO vo);

    boolean updateLawyerAuth(LawyerAuth lawyerAuth);

    void savaOrUpdateAuth(LawyerAuth lawyerAuth);

    PageResult<LawyerAuthDTO> getHomeLawyerList(ConditionVO vo);

    List<ConsultUserDTO> getChatUserList();
}
