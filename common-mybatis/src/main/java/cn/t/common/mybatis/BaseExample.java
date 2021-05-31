package cn.t.common.mybatis;

/**
 * @author <a href="mailto:yangjian@liby.ltd">研发部-杨建</a>
 * @version V1.0
 * @since 2021-03-17 10:27
 **/
public class BaseExample {
    private Integer pageNumber;
    private Integer pageSize;
    private Boolean countSql;
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getCountSql() {
        return countSql;
    }

    public void setCountSql(Boolean countSql) {
        this.countSql = countSql;
    }

    @Override
    public String toString() {
        return "BaseExample{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", countSql=" + countSql +
                '}';
    }
}
