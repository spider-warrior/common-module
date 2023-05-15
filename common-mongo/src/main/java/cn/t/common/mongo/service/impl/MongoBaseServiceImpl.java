package cn.t.common.mongo.service.impl;

import cn.t.common.mongo.entity.MongoBaseEntity;
import cn.t.common.mongo.service.MongoBaseService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.List;

public class MongoBaseServiceImpl<T extends MongoBaseEntity<PK>, PK extends Serializable, R extends MongoRepository<T, PK>> implements MongoBaseService<T, PK, R> {

    protected final R repository;
    protected final MongoTemplate mongoTemplate;

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long count(Example<T> example) {
        return repository.count();
    }

    @Override
    public boolean existsById(PK id) {
        return repository.existsById(id);
    }

    @Override
    public boolean exists(Example<T> example) {
        return repository.exists(example);
    }

    @Override
    public T findById(PK id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Example<T> example) {
        return repository.findAll(example);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<T> findAll(Example<T> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    @Override
    public T insert(T t) {
        beforeSave(t);
        return repository.insert(t);
    }

    @Override
    public List<T> insert(Iterable<T> ts) {
        for (T t : ts) {
            beforeSave(t);
        }
        return repository.insert(ts);
    }

    @Override
    public T save(T t) {
        if(t.getId() == null) {
            beforeSave(t);
        } else {
            beforeModify(t);
        }
        return repository.save(t);
    }

    @Override
    public List<T> saveAll(Iterable<T> ts) {
        for (T t : ts) {
            if(t.getId() == null) {
                beforeSave(t);
            } else {
                beforeModify(t);
            }
        }
        return repository.saveAll(ts);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void deleteAll(Iterable<T> ts) {
        repository.deleteAll(ts);
    }

    @Override
    public void removeById(PK id) {
        repository.deleteById(id);
    }

    private void beforeSave(T t) {
        if(t.getCrTime() == null) {
            t.setCrTime(System.currentTimeMillis());
        }
    }

    private void beforeModify(T t) {
        t.setUpTime(System.currentTimeMillis());
    }

    public MongoBaseServiceImpl(R repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }
}
