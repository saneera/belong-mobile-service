package com.belong.mobile.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;

/**
 * Utility Class for pagination
 */

public final class PaginationUtil {

    public static HttpHeaders generatePaginationHttpHeaders(Page page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + Long.toString(page.getTotalElements()));
        return headers;
    }


    public static PageRequest buildPageRequest(int pageNo, int pageSize, String sortBy, String sortDirection) {
        String[] sortByArray = sortBy.split(":");
        Sort.Direction defaultSortOrder = Sort.Direction.ASC;
        if (sortDirection == ConstantHelper.PAGE_SORT_DIRECTION_DESC) {
            defaultSortOrder = Sort.Direction.DESC;
        }
        return PageRequest.of(pageNo, pageSize, Sort.by(defaultSortOrder, sortByArray[0]));
    }
}
