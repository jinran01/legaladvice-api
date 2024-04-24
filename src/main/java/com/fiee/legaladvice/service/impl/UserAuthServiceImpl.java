package com.fiee.legaladvice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.dto.EmailDTO;
import com.fiee.legaladvice.dto.UserBackDTO;
import com.fiee.legaladvice.dto.UserDetailDTO;
import com.fiee.legaladvice.dto.UserOnlineDTO;
import com.fiee.legaladvice.entity.UserAuth;
import com.fiee.legaladvice.entity.UserInfo;
import com.fiee.legaladvice.entity.UserRole;
import com.fiee.legaladvice.exception.BizException;
import com.fiee.legaladvice.service.RedisService;
import com.fiee.legaladvice.service.UserAuthService;
import com.fiee.legaladvice.mapper.UserAuthMapper;
import com.fiee.legaladvice.service.UserInfoService;
import com.fiee.legaladvice.service.UserRoleService;
import com.fiee.legaladvice.utils.UserUtils;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fiee.legaladvice.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.fiee.legaladvice.constant.RedisPrefixConst.*;
import static com.fiee.legaladvice.enums.EmailEnum.COMMON_CODE;
import static com.fiee.legaladvice.utils.CommonUtils.checkEmail;
import static com.fiee.legaladvice.utils.CommonUtils.getRandomCode;

/**
* @author Fiee
* @description 针对表【tb_user_auth】的数据库操作Service实现
* @createDate 2024-03-02 19:35:41
*/
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth>
    implements UserAuthService{
    @Autowired
    private UserAuthMapper userAuthMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisService redisService;
    @Resource
    private HttpServletRequest request;
    @Override
    public PageResult<UserBackDTO> getUserList(ConditionVO condition) {
        //获取用户数量
        Integer count = userAuthMapper.countUser(condition);
        if (count == 0){
            return new PageResult<>();
        }
        List<UserBackDTO> listUsers = userAuthMapper.listUsers((condition.getCurrent()  - 1) *condition.getSize(), condition.getSize(), condition);
        return new PageResult<>(listUsers,count);
    }

    /**
     * 修改用户禁用状态
     * @param userAuth
     * @return
     */
    @Override
    public boolean updateUserState(UserAuth userAuth) {
        //获取该用户信息
        Integer infoId = userAuth.getUserInfoId();
        UserInfo userInfo = userInfoService.getById(infoId);
        int isDisable = userInfo.getIsDisable();
        //修改状态
        if (isDisable == 0){
            userInfo.setIsDisable(1);
        }else {
            userInfo.setIsDisable(0);
        }
        boolean update = userInfoService.updateById(userInfo);
        return update;
    }

    /**
     * 获取在线用户
     *
     * @return
     */
    @Override
    public PageResult<UserOnlineDTO> getOnlineUsers(ConditionVO conditionVO) {
        List<UserOnlineDTO> list = sessionRegistry.getAllPrincipals().stream()
                .filter(item -> sessionRegistry.getAllSessions(item, false).size() > 0)
                .map(item -> JSON.parseObject(JSON.toJSONString(item), UserOnlineDTO.class))
                .filter(item -> StringUtils.isBlank(conditionVO.getKeywords()) || item.getNickname().contains(conditionVO.getKeywords()))
                .sorted(Comparator.comparing(UserOnlineDTO::getLastLoginTime).reversed())
                .collect(Collectors.toList());
        // 执行分页
//        int fromIndex = getLimitCurrent().intValue();
//        int size = getSize().intValue();
//        int toIndex = list.size() - fromIndex > size ? fromIndex + size : list.size();
//        List<UserOnlineDTO> userOnlineList = list.subList(fromIndex, toIndex);
//        return new PageResult<>(userOnlineList, list.size());
        int size = conditionVO.getSize().intValue();
        int fromIndex = (conditionVO.getCurrent().intValue() - 1 ) * size;
        int current = list.size() - fromIndex > size ? fromIndex + size : list.size();
        List<UserOnlineDTO> userOnlineList = list.subList(fromIndex, current);
        return new PageResult<>(userOnlineList,list.size());
    }

    /**
     * 下线用户
     * @param userInfoId
     * @return
     */
    @Override
    public String removeUser(Integer userInfoId) {
        if (UserUtils.getLoginUser().getUserInfoId().equals(userInfoId)){
            return "咋地?狠起来连自己都不放过?" ;
        }else {
            //获取用户session
            List<Object> userInfoList =sessionRegistry.getAllPrincipals().stream().
                    filter(item->{
                        UserDetailDTO userDetailDTO = (UserDetailDTO) item;
                        return userDetailDTO.getUserInfoId().equals(userInfoId);
                    }).collect(Collectors.toList());
            List<SessionInformation> allSessions = new ArrayList<>();
            userInfoList.forEach(item -> allSessions.addAll(sessionRegistry.getAllSessions(item, false)));
            // 注销session
            allSessions.forEach(SessionInformation::expireNow);
            return "已下线该用户！" ;
        }
    }


    /**
     * 修改用户角色或者昵称
     * @param map
     * @return
     */

    @Override
    @Transactional
    public boolean updateUserRole(Map map) {
        //删除原有的信息
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        //获取userId
        Integer userInfoId = (Integer) map.get("userInfoId");
        Integer userAuthId = (Integer) map.get("userAuthId");
        wrapper.eq(UserRole::getUserId,userAuthId);
        userRoleService.remove(wrapper);

        //修改角色
        //TODO 进行优化
        List<Integer> roleIds = (List) map.get("roleList");
        List<UserRole> list = new ArrayList<>();
        for (int id : roleIds) {
            UserRole userRole= new UserRole();
            userRole.setUserId(userAuthId);
            userRole.setRoleId(id);
            list.add(userRole);
        }
        userRoleService.saveBatch(list);

        //修改用户信息
        UserInfo userInfo = userInfoService.getById(userInfoId);
        userInfo.setNickname((String) map.get("nickname"));
        userInfo.setUpdateTime(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
        userInfoService.updateById(userInfo);
        return true;
    }

    @Override
    public boolean updateUserPass(Map map) {
        Integer userId = (Integer) map.get("id");
        //获取用户信息
        UserAuth userAuth = this.getById(userId);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean flag = passwordEncoder.matches(
                (CharSequence) map.get("old_password"),
                userAuth.getPassword());
        //判断旧密码是否匹配
        if (flag){
            String new_password = passwordEncoder.encode((CharSequence) map.get("new_password"));
            userAuth.setPassword(new_password);
            this.updateById(userAuth);
        }else {
            return false;
        }
        return true;
    }


    @Override
    public void sendCode(String username) {
        if (!checkEmail(username)){
            throw new BizException("请输入正确的邮箱");
        }
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject(COMMON_CODE.getSubject())
                .template(COMMON_CODE.getTemplate())
                .build();
        //获取验证码
        String code = getRandomCode(6);
        //验证码存入redis 过期时间15分钟
        redisService.set(USER_CODE_KEY + username , code , CODE_EXPIRE_TIME);
        //mq交换机
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE,"*", new Message(JSON.toJSONBytes(emailDTO),new MessageProperties()));
    }

    @Override
    public boolean updateUserInfo(UserInfo userInfo) {
        return userInfoService.updateById(userInfo);
    }
}




