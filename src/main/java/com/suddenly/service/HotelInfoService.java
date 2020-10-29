package com.suddenly.service;

import com.github.pagehelper.PageInfo;
import com.suddenly.base.PageDto;
import com.suddenly.entity.HotelInfo;
import com.suddenly.entity.request.HotelDto;
import com.suddenly.entity.request.HotelPageDto;
import com.suddenly.entity.request.HotelVoPageDto;
import com.suddenly.entity.response.HotelInfoVo;
import com.suddenly.entity.response.HotelVo;
import com.suddenly.exception.CustomizeException;


public interface HotelInfoService {

    /**
     * 查询酒店
     * @Param hotelPageDto
     * @return PageInfo<HotelInfoVo>
     */
    PageInfo<HotelInfoVo> queryPage(HotelPageDto hotelPageDto) throws CustomizeException;

    /**
     * 查询酒店详情
     * @Param id
     * @return HotelInfoVo
     */
    HotelInfo queryDetails(Long id) throws CustomizeException;

    /**
     * 查看预定过的酒店
     */
    PageInfo<HotelInfoVo> queryBookedHotel(PageDto pageDto) throws CustomizeException;

    /**
     * 维护酒店信息
     */
    Boolean updateHotel(HotelDto hotelDto) throws CustomizeException;

    /**
     * 查询酒店是否存在工作人员
     */
    PageInfo<HotelVo> queryHotel(HotelVoPageDto hotelVoPageDto) throws CustomizeException;

}
