package com.suddenly.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suddenly.base.PageDto;
import com.suddenly.entity.HotelInfo;
import com.suddenly.entity.UserCredit;
import com.suddenly.entity.UserInfo;
import com.suddenly.entity.request.UserInfoDto;
import com.suddenly.entity.response.UserInfoVo;
import com.suddenly.enums.VipEnum;
import com.suddenly.exception.CustomizeException;
import com.suddenly.mapper.HotelInfoMapper;
import com.suddenly.mapper.UserCreditMapper;
import com.suddenly.mapper.UserInfoMapper;
import com.suddenly.responseResult.ResultEnum;
import com.suddenly.service.UserInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserCreditMapper userCreditMapper;

    @Autowired
    private HotelInfoMapper hotelInfoMapper;

    @Override
    public UserInfoVo queryById(Long id) throws CustomizeException{
        try {
            UserInfo userInfo = userInfoMapper.queryById(id);
            UserInfoVo userInfoVo = new UserInfoVo();
            BeanUtils.copyProperties(userInfo, userInfoVo);
            return userInfoVo;
        } catch (CustomizeException e) {
            logger.error("查询用户失败");
            throw new CustomizeException(e.getMessage(), ResultEnum.QUERY_USER_FAILURE);
        }
    }

    @Override
    public List<UserInfoVo> queryVo(UserInfoDto userInfoDto) throws CustomizeException {
        try {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userInfoDto, userInfo);
            List<UserInfoVo> userInfoVos = new ArrayList<>();
            List<UserInfo> userInfos = userInfoMapper.query(userInfo);
            userInfos.stream().forEach(user -> {
                UserInfoVo userInfoVo = new UserInfoVo();
                BeanUtils.copyProperties(user, userInfoVo);
                userInfoVos.add(userInfoVo);
            });
            return userInfoVos;
        } catch (CustomizeException e) {
            logger.error("查询用户失败");
            throw new CustomizeException(e.getMessage(), ResultEnum.QUERY_USER_FAILURE);
        }
    }

    @Override
    public PageInfo<UserInfoVo> queryPage(PageDto pageDto) throws CustomizeException {
        try {
            PageHelper.startPage(pageDto.getPageNo(), pageDto.getPageSize(), "birthday DESC");
            List<UserInfoVo> userInfoVos = new ArrayList<>();
            List<UserInfo> userInfos = userInfoMapper.query(new UserInfo());
            userInfos.stream().forEach(user -> {
                UserInfoVo userInfoVo = new UserInfoVo();
                BeanUtils.copyProperties(user, userInfoVo);
                userInfoVos.add(userInfoVo);
            });
            PageInfo pageInfo = new PageInfo(userInfos);
            pageInfo.setList(userInfoVos);
            return pageInfo;
        } catch (CustomizeException e) {
            logger.error("查询用户失败");
            throw new CustomizeException(e.getMessage(), ResultEnum.QUERY_USER_FAILURE);
        }
    }

    @Override
    public Boolean addUserInfo(UserInfoDto userInfoDto) throws CustomizeException {
        try {
            if (userInfoDto.getHotelId() != null) {
                List<UserInfo> userInfos = userInfoMapper.queryHotelStaff(userInfoDto.getHotelId());
                HotelInfo hotelInfo = hotelInfoMapper.queryById(userInfoDto.getHotelId());
                userInfos.stream().forEach(userInfo -> {
                    if (userInfo.getHotelId().equals(hotelInfo.getId())) {
                        throw new CustomizeException(ResultEnum.HOTEL_STAFF_EXISTED);
                    }
                });
            }
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userInfoDto, userInfo);
            userInfoMapper.addUserInfo(userInfo);
            return true;
        } catch (CustomizeException e) {
            logger.error(e.getResultEnum());
            throw new CustomizeException(e, e.getResultEnum());
        } catch (Exception e) {
            logger.error("新增用户失败");
            throw new CustomizeException(e.getMessage(), ResultEnum.ADD_USER_FAILURE);
        }
    }

    @Override
    public Boolean updateUserInfo(UserInfoDto userInfoDto) throws CustomizeException {
        try {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userInfoDto, userInfo);
            userInfoMapper.updateUserInfo(userInfo);
            return true;
        } catch (CustomizeException e) {
            logger.error("修改用户失败");
            throw new CustomizeException(e.getMessage(), ResultEnum.UPDATE_USER_FAILURE);
        }
    }

    @Override
    public Boolean deleteById(Long id) throws CustomizeException {
        try {
            userInfoMapper.deleteById(id);
            return true;
        } catch (CustomizeException e) {
            logger.error("删除用户失败");
            throw new CustomizeException(e.getMessage(), ResultEnum.DELETE_USER_FAILURE);
        }
    }

    @Override
    public Boolean rechargeCredit(Long userId, Double money) throws CustomizeException {
        try {
            UserInfo info = userInfoMapper.queryById(userId);
            Double resultCredit = info.getCredit() + (money * 10);
            UserInfo userInfo = new UserInfo();
            userInfo.setId(userId);
            userInfo.setCredit(resultCredit);
            if (resultCredit >= 0 && resultCredit < 2000) {
                userInfo.setVip(VipEnum.ONE.getValue());
            } else if (resultCredit >= 2000 && resultCredit < 4000) {
                userInfo.setVip(VipEnum.TWO.getValue());
            } else if (resultCredit >= 4000 && resultCredit < 6000) {
                userInfo.setVip(VipEnum.THREE.getValue());
            } else if (resultCredit >= 6000 && resultCredit < 8000) {
                userInfo.setVip(VipEnum.FOUR.getValue());
            } else if (resultCredit >= 8000) {
                userInfo.setVip(VipEnum.FIVES.getValue());
            }
            userInfoMapper.updateUserInfo(userInfo);

            UserCredit userCredit = new UserCredit();
            userCredit.setUserId(userId);
            userCredit.setTime(new Date());
            userCredit.setAction("充值");
            userCredit.setCreditChange("+" + (money * 10));
            userCredit.setCreditResult(resultCredit);
            userCreditMapper.insert(userCredit);

            return true;
        } catch (CustomizeException e) {
            logger.error("充值信用失败");
            throw new CustomizeException(ResultEnum.RECHARGE_CREDIT_FAILURE);
        }
    }

}
