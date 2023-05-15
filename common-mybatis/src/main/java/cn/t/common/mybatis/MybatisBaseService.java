package cn.t.common.mybatis;

import cn.t.common.entity.BaseEntity;
import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

public interface MybatisBaseService<T extends BaseEntity<PK>, E, PK extends Serializable, M extends BaseMapper<T, E, PK>> {

    T queryByPrimaryKey(PK id);

    List<T> queryByExample(E e);

    Page<T> queryPageByExample(E e);

    Page<T> queryPageByExampleWithBlobs(E e);

    List<T> queryAll();

    void removeByPrimaryKey(PK id);

    void save(T t);

    void saveSelective(T t);

    void saveList(List<T> list);

    void modifyByPrimaryKey(T t);

    void modifyByPrimaryKeySelective(T t);

    M getMapper();
}
