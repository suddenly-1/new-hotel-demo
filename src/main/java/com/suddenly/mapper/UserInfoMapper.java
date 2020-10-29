package com.suddenly.mapper;

import com.suddenly.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserInfoMapper {

    /**
     * 根据账号查询用户
     * @param account
     * @return UserInfo
     */
    UserInfo queryByAccount(@Param("account") String account);

    /**
     * 根据id查询用户
     * @param id
     * @return UserInfo
     */
    UserInfo queryById(@Param("id") Long id);

    /**
     * 查询用户
     * @param userInfo
     * @return List<UserInfo>
     */
    List<UserInfo> query(UserInfo userInfo);

    /**
     * 新增用户
     * @param userInfo
     */
    void addUserInfo(UserInfo userInfo);

    /**
     * 修改用户信息
     * @param userInfo
     */
    void updateUserInfo(UserInfo userInfo);

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteById(@Param("id") Long id);

    /**
     * 查询酒店的工作人员
     * @param hotelId
     */
    List<UserInfo> queryHotelStaff(@Param("hotelId") Long hotelId);

}
