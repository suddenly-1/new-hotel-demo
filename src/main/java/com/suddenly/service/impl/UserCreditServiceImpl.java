package com.suddenly.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suddenly.entity.UserCredit;
import com.suddenly.entity.request.CreditPageDto;
import com.suddenly.entity.response.UserCreditVo;
import com.suddenly.exception.CustomizeException;
import com.suddenly.mapper.UserCreditMapper;
import com.suddenly.responseResult.ResultEnum;
import com.suddenly.service.UserCreditService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCreditServiceImpl implements UserCreditService {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private UserCreditMapper userCreditMapper;

    @Override
    public PageInfo<UserCreditVo> queryPage(CreditPageDto creditPageDto) throws CustomizeException {
        try {
            PageHelper.startPage(creditPageDto.getPageNo(), creditPageDto.getPageSize());
            List<UserCredit> userCredits = userCreditMapper.queryList(creditPageDto.getUserId());
            List<UserCreditVo> userCreditVos = new ArrayList<>();
            userCredits.stream().forEach(userCredit -> {
                UserCreditVo userCreditVo = new UserCreditVo();
                BeanUtils.copyProperties(userCredit, userCreditVo);
                userCreditVos.add(userCreditVo);
            });
            PageInfo pageInfo = new PageInfo(userCredits);
            pageInfo.setList(userCreditVos);
            return pageInfo;
        } catch (CustomizeException e) {
            logger.error("查询信用失败");
            throw new CustomizeException(ResultEnum.QUERY_CREDIT_FAILURE);
        }
    }


}
