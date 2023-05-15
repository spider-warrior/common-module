package cn.t.common.mybatis;

import java.util.List;

public class PageHelperPageableParam {

    private int pageNumber = 1;
    private int pageSize = 10;
    private List<String> orderByList;
    private Boolean countSql;

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

    public String getOrderByClause() {
        return buildOrderByClause(orderByList);
    }

    protected String buildOrderByClause(List<String> orderByList) {
        if(orderByList == null || orderByList.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for(String orderBy: orderByList) {
            String[] elements = orderBy.split(",");
            builder.append(elements[0]);
            if(elements.length == 1) {
                builder.append(" asc");
            } else {
                builder.append(" ").append(elements[1]);
            }
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public Boolean getCountSql() {
        return countSql;
    }

    public void setCountSql(Boolean countSql) {
        this.countSql = countSql;
    }

    @Override
    public String toString() {
        return "MybatisPageableParam{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", orderByList=" + orderByList +
                ", countSql=" + countSql +
                '}';
    }
}
