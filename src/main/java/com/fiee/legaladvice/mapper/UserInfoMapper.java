package com.fiee.legaladvice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.dto.ConsultUserDTO;
import com.fiee.legaladvice.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_user_info】的数据库操作Mapper
* @createDate 2024-03-02 19:39:16
* @Entity com.fiee.legaladvice.UserInfo
*/
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    List<ConsultUserDTO> getConsultUserList();
}




