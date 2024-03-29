package cn.t.common.rpc;

import java.util.List;

public class RpcPageableParam {
    private int pageNumber = 0;
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
        return "PageableRequestParam{" +
            "pageNumber=" + pageNumber +
            ", pageSize=" + pageSize +
            ", orderByList=" + orderByList +
            '}';
    }
}
