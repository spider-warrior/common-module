package cn.t.common.mongo.service;

import cn.t.common.mongo.entity.MongoBaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.List;

public interface MongoBaseService<T extends MongoBaseEntity<PK>, PK extends Serializable, R extends MongoRepository<T, PK>> {
    long count();
    long count(Example<T> example);
    boolean existsById(PK id);
    boolean exists(Example<T> example);
    T findById(PK id);
    List<T> findAll();
    List<T> findAll(Example<T> example);
    Page<T> findAll(Pageable pageable);
    Page<T> findAll(Example<T> example, Pageable pageable);
    T insert(T t);
    List<T> insert(Iterable<T> ts);
    T save(T t);
    List<T> saveAll(Iterable<T> ts);
    void deleteAll();
    void deleteAll(Iterable<T> ts);
    void removeById(PK id);
}
