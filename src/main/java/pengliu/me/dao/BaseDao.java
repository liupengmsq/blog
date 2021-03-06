package pengliu.me.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T>
{
    //根据id加载实体
    T get(Serializable id);

    //保存实体
    void persist(T entity);

    //更新实体
    void update(T entity);

    //删除实体
    void delete(T entity);

    //根据id删除实体
    void delete(Serializable id);

    //获取所有实体
    List<T> findAll();

    //获取实体总数
    long findCount();
}
