package cn.t.common.mybatis;

import cn.t.common.entity.BaseEntity;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author <a href="mailto:yangjian@liby.ltd">研发部-杨建</a>
 * @version V1.0
 * @since 2021-03-17 10:32
 **/
public abstract class MybatisBaseServiceImpl<T extends BaseEntity<PK>, E, PK extends Serializable, M extends BaseMapper<T, E, PK>> implements MybatisBaseService<T, E, PK, M> {

    protected final M baseMapper;

    @Override
    public T queryByPrimaryKey(PK id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> queryByExample(E e) {
        return baseMapper.selectByExample(e);
    }

    @Override
    public Page<T> queryPageByExample(E e) {
        return PageHelper.startPage(e).doSelectPage(() -> baseMapper.selectByExample(e));
    }

    @Override
    public Page<T> queryPageByExampleWithBlobs(E e) {
        return PageHelper.startPage(e).doSelectPage(() -> baseMapper.selectByExampleWithBLOBs(e));
    }

    @Override
    public List<T> queryAll() {
        return baseMapper.selectAll();
    }

    public long countByExample(E e) {
        return baseMapper.countByExample(e);
    }

    @Override
    public void removeByPrimaryKey(PK id) {
        baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(T t) {
        beforeSave(t);
        baseMapper.insert(t);
    }

    @Override
    public void saveSelective(T t) {
        beforeSave(t);
        baseMapper.insertSelective(t);
    }

    private void beforeSave(T t) {
        if(t.getCrTime() == null) {
            t.setCrTime(LocalDateTime.now());
        }
    }

    @Override
    public void saveList(List<T> list) {
        baseMapper.insertList(list);
    }

    @Override
    public void modifyByPrimaryKey(T t) {
        beforeModify(t);
        baseMapper.updateByPrimaryKey(t);
    }

    @Override
    public void modifyByPrimaryKeySelective(T t) {
        beforeModify(t);
        baseMapper.updateByPrimaryKeySelective(t);
    }

    private void beforeModify(T t) {
        t.setUpTime(LocalDateTime.now());
    }

    public MybatisBaseServiceImpl(M baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public M getMapper() {
        return baseMapper;
    }
}
