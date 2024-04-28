package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.dto.ConsultUserDTO;
import com.fiee.legaladvice.dto.LawyerAuthDTO;
import com.fiee.legaladvice.entity.ChatRecord;
import com.fiee.legaladvice.entity.LawyerAuth;
import com.fiee.legaladvice.entity.UserRole;
import com.fiee.legaladvice.mapper.ChatRecordMapper;
import com.fiee.legaladvice.mapper.LawyerAuthMapper;
import com.fiee.legaladvice.mapper.UserInfoMapper;
import com.fiee.legaladvice.service.ChatRecordService;
import com.fiee.legaladvice.service.LawyerAuthService;
import com.fiee.legaladvice.service.RedisService;
import com.fiee.legaladvice.service.UserRoleService;
import com.fiee.legaladvice.utils.UserUtils;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.fiee.legaladvice.constant.RedisPrefixConst.LAWYER_LIKE_COUNT;

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
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ChatRecordMapper chatRecordMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public PageResult<LawyerAuth> getLawyerList(ConditionVO vo) {
        List<LawyerAuth> list = baseMapper.getLawyerList(vo, (vo.getCurrent() - 1) * vo.getSize(), vo.getSize());
        Integer count = Math.toIntExact(this.count());
        return new PageResult<>(list, count);
    }

    /**
     * 律师认证审核
     *
     * @param lawyerAuth
     */
    @Override
    public boolean updateLawyerAuth(LawyerAuth lawyerAuth) {
        boolean flag = false;
        //未通过
        if (lawyerAuth.getStatus() == 3) {
            this.saveOrUpdate(lawyerAuth);
        } else {  //通过
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

    /**
     * 新增认证或者更新认证
     *
     * @param lawyerAuth
     */
    @Override
    public void savaOrUpdateAuth(LawyerAuth lawyerAuth) {
        Integer id = UserUtils.getLoginUser().getId();
        lawyerAuth.setUserAuthId(id);
        this.saveOrUpdate(lawyerAuth);
    }

    @Override
    public PageResult<LawyerAuthDTO> getHomeLawyerList(ConditionVO vo) {
        List<LawyerAuthDTO> list = baseMapper.getHomeLawyerList(vo, (vo.getCurrent() - 1) * vo.getSize(), vo.getSize());
        List<LawyerAuthDTO> collect = list.stream().map(item -> {
            item.setLikeCount(redisService.zScore(LAWYER_LIKE_COUNT, item.getUserAuthId()));
            return item;
        }).collect(Collectors.toList());
        Integer count = Math.toIntExact(this.count());
        return new PageResult<>(collect, count);
    }

    @Override
    public List<ConsultUserDTO> getChatUserList() {
        Integer userId = UserUtils.getLoginUser().getId();
        LambdaQueryWrapper<ChatRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatRecord::getUserId,userId);
        List<ChatRecord> chatRecordList = chatRecordMapper.selectList(wrapper);
        //TODO  优化与用户对话的用户列表
        //获取到所有的toUserIds
        Set<Integer> collect = new HashSet<>();
        for (ChatRecord chatRecord:chatRecordList) {
            if (Objects.nonNull(chatRecord.getToUserId())){
                collect.add(chatRecord.getToUserId());
            }
        }
        //获取对应的用户信息
        List<ConsultUserDTO> userList = userInfoMapper.getConsultUserList();
        List<ConsultUserDTO> consultUserDTOList = new ArrayList<>();
        for (ConsultUserDTO consultUserDTO : userList) {
            if (collect.contains(consultUserDTO.getUserAuthId())) {
                consultUserDTOList.add(consultUserDTO);
            }
        }
        return consultUserDTOList;
    }
}
