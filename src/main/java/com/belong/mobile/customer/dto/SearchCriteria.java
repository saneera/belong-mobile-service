package com.belong.mobile.customer.dto;

import com.belong.mobile.common.ConstantHelper;

public class SearchCriteria {

    private Integer page = Integer.parseInt(ConstantHelper.DEFAULT_PAGE);

    private Integer size = Integer.parseInt(ConstantHelper.DEFAULT_PAGE_SIZE);

    private String sortBy = ConstantHelper.DEFAULT_PAGE_SORT_COLUMN;

    private String sortDirection = ConstantHelper.PAGE_SORT_DIRECTION_ASC;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

}
