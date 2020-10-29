package com.suddenly.base;

import com.suddenly.base.BaseDto;

public class PageDto extends BaseDto {
    private Integer pageNo;
    private Integer pageSize;

    public PageDto() {
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
