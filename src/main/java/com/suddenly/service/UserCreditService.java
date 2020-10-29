package com.suddenly.service;

import com.github.pagehelper.PageInfo;
import com.suddenly.entity.request.CreditPageDto;
import com.suddenly.entity.response.UserCreditVo;
import com.suddenly.exception.CustomizeException;

public interface UserCreditService {

    /**
     * 查询信用
     * @Param creditPageDto
     * @return UserCreditVo
     */
    PageInfo<UserCreditVo> queryPage(CreditPageDto creditPageDto) throws CustomizeException;

}
