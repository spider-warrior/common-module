package cn.t.common.mybatis.idgenerator;

/**
 * 不支持的数据库类型
 *
 * @author yj
 * @since 2020-05-07 12:41
 **/
public class UnSupportedDatabase extends RuntimeException {

    private String databaseName;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public UnSupportedDatabase(String databaseName) {
        super("不支持的数据库: " + databaseName);
        this.databaseName = databaseName;
    }
}
