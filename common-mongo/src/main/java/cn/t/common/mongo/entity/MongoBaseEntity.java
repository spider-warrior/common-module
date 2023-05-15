package cn.t.common.mongo.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class MongoBaseEntity<Key extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    protected Key id;

    /**
     * 创建时间
     * */
    protected Long crTime;

    /**
     * 更新时间
     * */
    protected Long upTime;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public Long getCrTime() {
        return crTime;
    }

    public void setCrTime(Long crTime) {
        this.crTime = crTime;
    }

    public Long getUpTime() {
        return upTime;
    }

    public void setUpTime(Long upTime) {
        this.upTime = upTime;
    }

    @Override
    public String toString() {
        return "MongoBaseEntity{" +
                "id='" + id + '\'' +
                ", crTime=" + crTime +
                ", upTime=" + upTime +
                '}';
    }
}
