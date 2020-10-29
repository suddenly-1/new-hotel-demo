package com.suddenly.service;

import com.github.pagehelper.PageInfo;
import com.suddenly.base.PageDto;
import com.suddenly.entity.request.UserInfoDto;
import com.suddenly.entity.response.UserInfoVo;
import com.suddenly.exception.CustomizeException;

import java.util.List;

public interface UserInfoService {

    /**
     * 根据id查询用户
     * @param id
     * @return UserInfo
     */
    UserInfoVo queryById(Long id) throws CustomizeException;

    /**
     * 查询用户
     * @param userInfoDto
     * @return List<UserInfo>
     */
    List<UserInfoVo> queryVo(UserInfoDto userInfoDto) throws CustomizeException;

    /**
     * 分页查询
     * @param pageDto
     * @return List<UserInfo>
     */
    PageInfo<UserInfoVo> queryPage(PageDto pageDto) throws CustomizeException;

    /**
     * 新增用户
     * @param userInfoDto
     */
    Boolean addUserInfo(UserInfoDto userInfoDto) throws CustomizeException;

    /**
     * 修改用户信息
     * @param userInfoDto
     */
    Boolean updateUserInfo(UserInfoDto userInfoDto) throws CustomizeException;

    /**
     * 根据id删除用户
     * @param id
     */
    Boolean deleteById(Long id) throws CustomizeException;

    /**
     * 充值信用
     */
    Boolean rechargeCredit(Long userId, Double money) throws CustomizeException;

}
