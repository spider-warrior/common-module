package cn.t.common.mongo.entity;

import org.springframework.data.annotation.Version;

import java.io.Serializable;

public class MongoVersionedBaseEntity<Key extends Serializable> extends MongoBaseEntity<Key> {

    private static final long serialVersionUID = 1L;

    @Version
    protected int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "MongoVersionedBaseEntity{" +
                "version=" + version +
                "} " + super.toString();
    }
}
