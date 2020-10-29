package com.suddenly.mapper;

import com.suddenly.entity.UserCredit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserCreditMapper {

    /**
     * 查询信用
     * @Param userId
     * @return UserCreditVo
     */
    List<UserCredit> queryList(@Param("userId") Long userId);

    /**
     * 新增信用
     * @Param
     */
    void insert(UserCredit userCredit);



}
