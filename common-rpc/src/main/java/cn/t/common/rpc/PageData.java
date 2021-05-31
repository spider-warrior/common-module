package cn.t.common.rpc;

import java.util.Collections;
import java.util.List;

public class PageData<T> {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    private static final PageData EMPTY_PAGE = new PageData<>(1, 0, 0, Collections.emptyList());
    /**
     * 页码，从1开始
     */
    private int pageNum;
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

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
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
        return "PageData{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", list=" + list +
                '}';
    }

    public PageData() {
    }

    public PageData(int pageNum, int pageSize, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    @SuppressWarnings("unchecked")
    public static <T> PageData<T> empty() {
        return (PageData<T>)EMPTY_PAGE;
    }

}
