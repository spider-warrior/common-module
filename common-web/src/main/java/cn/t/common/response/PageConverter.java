package cn.t.common.response;


import cn.t.common.rpc.PageBo;
import cn.t.common.rpc.RpcPageableParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageConverter {

    public static PageVo toPageVo(Page<?> page, List<?> voList) {
        if (page == null) {
            return PageVo.empty();
        }
        return new PageVo(page.getNumber() + 1, page.getSize(), page.getTotalElements(), voList);
    }

    public static PageVo toPageVo(PageBo<?> page, List<?> voList) {
        if (page == null) {
            return PageVo.empty();
        }
        return new PageVo(page.getPageNumber() + 1, page.getPageSize(), page.getTotal(), voList);
    }

    public static Pageable toPageable(RequestPageableParam pageableParam) {
        if(pageableParam == null) {
            return PageRequest.of(0, 10);
        }
        int pageNumber = pageableParam.getPageNumber();
        if(pageNumber < 0) {
            pageNumber = 0;
        } else if(pageNumber > 0) {
            pageNumber--;
        }
        return PageRequest.of(pageNumber, pageableParam.getPageSize(), toSort(pageableParam.getOrderByList()));
    }

    public static Pageable toPageable(RpcPageableParam pageableParam) {
        if(pageableParam == null) {
            return PageRequest.of(0, 10);
        }
        int pageNumber = pageableParam.getPageNumber();
        if(pageNumber < 0) {
            pageNumber = 0;
        }
        return PageRequest.of(pageNumber, pageableParam.getPageSize(), toSort(pageableParam.getOrderByList()));
    }

    public static <T> PageBo<T> toPageBo(Page<?> page, List<T> voList) {
        if(page == null) {
            return PageBo.empty();
        }
        PageBo<T> bo = new PageBo<>();
        bo.setPageNumber(page.getNumber());
        bo.setPageSize(page.getSize());
        bo.setTotal(page.getTotalElements());
        bo.setList(voList);
        return bo;
    }

    public static void requestPageableToRpcPageable(RequestPageableParam src, RpcPageableParam to) {
        if(src != null && to != null) {
            int srcPageNumber = src.getPageNumber();
            if(srcPageNumber < 1) {
                srcPageNumber = 0;
            } else {
                srcPageNumber--;
            }
            to.setPageNumber(srcPageNumber);
            to.setPageSize(src.getPageSize());
            to.setOrderByList(src.getOrderByList());
        }
    }

    private static Sort toSort(List<String> orderByList) {
        if(CollectionUtils.isEmpty(orderByList)) {
            return Sort.unsorted();
        }
        return Sort.by(toOrderList(orderByList));
    }

    public static List<Sort.Order> toOrderList(List<String> orderByClauseList) {
        if(CollectionUtils.isEmpty(orderByClauseList)) {
            return Collections.emptyList();
        }
        List<Sort.Order> orderByList = new ArrayList<>(orderByClauseList.size());
        orderByClauseList.forEach(orderByClause -> orderByList.add(toOrder(orderByClause)));
        return orderByList;
    }

    public static Sort.Order toOrder(String orderByClause) {
        if(StringUtils.hasText(orderByClause)) {
            String[] elements = orderByClause.split(",");
            if(elements.length == 1) {
                return Sort.Order.asc(elements[0]);
            } else {
                Sort.Direction direction = Sort.Direction.fromString(elements[1]);
                return new Sort.Order(direction, elements[0]);
            }
        } else {
            return null;
        }
    }
}
