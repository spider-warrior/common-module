package cn.t.common.rpc;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PageBo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    private static final PageBo EMPTY_PAGE = new PageBo<>(0, 10, 0, Collections.emptyList());
    /**
     * 页码，从1开始
     */
    private int pageNumber;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 总数
     */
    private long total;
    /**
     * 数据集合
     */
    private List<T> list;

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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBo{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", list=" + list +
                '}';
    }

    public PageBo() {
    }

    public PageBo(int pageNumber, int pageSize, long total, List<T> list) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    @SuppressWarnings("unchecked")
    public static <T> PageBo<T> empty() {
        return (PageBo<T>)EMPTY_PAGE;
    }

}
