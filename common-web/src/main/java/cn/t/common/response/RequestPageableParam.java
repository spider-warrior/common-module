package cn.t.common.response;

import java.util.List;

/**
 * 分页查询参数
 **/
public class RequestPageableParam {

    private int pageNumber = 1;
    private int pageSize = 10;
    private List<String> orderByList;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<String> orderByList) {
        this.orderByList = orderByList;
    }

    @Override
    public String toString() {
        return "RequestPageableParam{" +
            "pageNumber=" + pageNumber +
            ", pageSize=" + pageSize +
            ", orderByList=" + orderByList +
            '}';
    }
}
