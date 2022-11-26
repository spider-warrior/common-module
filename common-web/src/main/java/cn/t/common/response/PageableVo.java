package cn.t.common.response;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PageableVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final PageableVo EMPTY = new PageableVo(1, 0, 0, Collections.emptyList());

    /**
     * 页码，从1开始
     */
    private final int pageNumber;
    /**
     * 页面大小
     */
    private final int pageSize;
    /**
     * 总数
     */
    private final long total;
    /**
     * 总页数
     */
    private final int pages;
    /**
     * 数据
     * */
    private final List<?> list;

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotal() {
        return total;
    }

    public int getPages() {
        return pages;
    }

    public List<?> getList() {
        return list;
    }

    public PageableVo(int pageNumber, int pageSize, long total, List<?> list) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = total;
        if(pageSize == 0) {
            this.pages = 0;
        } else {
            this.pages = (int)((total + pageSize - 1) / pageSize);
        }
        this.list = list;
    }

    public static PageableVo empty() {
        return EMPTY;
    }

    public static PageableVo empty(int pageNumber, int pageSize) {
        return new PageableVo(pageNumber, pageSize, 0, Collections.emptyList());
    }

    @Override
    public String toString() {
        return "PageableVo{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
