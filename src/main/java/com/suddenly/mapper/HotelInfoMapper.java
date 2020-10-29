package com.suddenly.mapper;

import com.github.pagehelper.PageInfo;
import com.suddenly.entity.HotelInfo;
import com.suddenly.entity.request.HotelInfoDto;
import com.suddenly.entity.response.HotelInfoVo;
import com.suddenly.entity.response.HotelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelInfoMapper {

    /**
     * 查询可以通过,酒店名称、房间（类型、原始价格区间、有空房期间（房间数量、入住日期，退房日期））、星级
     * 进行搜索，这些条件可以独立起作用，也可以联合起作用
     * @Param hotelInfo
     */
    List<HotelInfoVo> queryAllList(HotelInfoDto hotelInfoDto);

    /**
     * 根据id查询酒店
     * @Param id
     */
    HotelInfo queryById(@Param("id") Long id);

    /**
     * 查询预定过的酒店
     */
    List<HotelInfo> queryBookedHotel(@Param("userId") Long userId);

    /**
     * 修改酒店信息
     */
    void updateHotel(HotelInfo hotelInfo);

    /**
     * 查询所有酒店
     */
    List<HotelInfoVo> queryAll();

    /**
     * 查询酒店是否存在工作人员
     */
//    List<HotelVo> queryHotel();

}
