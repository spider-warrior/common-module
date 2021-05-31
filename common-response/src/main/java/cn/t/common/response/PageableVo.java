package cn.t.common.response;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:yangjian@liby.ltd">研发部-杨建</a>
 * @version V1.0
 * @since 2021-03-17 10:21
 **/
public class PageableVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final PageableVo EMPTY = new PageableVo(1, 0, 0, Collections.emptyList());

    /**
     * 页码，从1开始
     */
    private final int pageNum;
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
    private final List<Object> list;

    public int getPageNum() {
        return pageNum;
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

    public List<Object> getList() {
        return list;
    }

    public PageableVo(int pageNum, int pageSize, long total, List<Object> list) {
        this.pageNum = pageNum;
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

    public static PageableVo empty(int pageNum, int pageSize) {
        return new <Object>PageableVo(pageNum, pageSize, 0, Collections.emptyList());
    }

    @Override
    public String toString() {
        return "PageableVo{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
