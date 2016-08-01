package pengliu.me.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseDaoHibernate4<T> implements BaseDao<T>
{
    private Class<T> entityClass;

    public BaseDaoHibernate4()
    {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.entityClass = (Class)params[0];
    }

    //DAO组件进行持久化操作底层依赖的SessionFactory组件
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory()
    {
        return this.sessionFactory;
    }

    //根据ID加载实体
    @SuppressWarnings("unchecked")
    public T get(Serializable id)
    {
        return (T)getSessionFactory().getCurrentSession().get(this.getEntityClass(), id);
    }

    public T get(String columnName, Serializable columnValue)
    {
        List<T> ret = getList(columnName, columnValue);
        if(ret != null && ret.size() > 0)
        {
            return ret.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public T findUniqueBy(String propertyName, Serializable value)
    {
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(new Criterion[] { criterion }).uniqueResult();
    }

    protected Criteria createCriteria(Criterion[] criterions)
    {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(getEntityClass());
        for (Criterion c : criterions)
        {
            criteria.add(c);
        }
        return criteria;
    }

    public List<T> getList(String columnName, Serializable columnValue)
    {
        return this.find("from " + this.getEntityClass().getSimpleName() + " en where en." + columnName + "=?", columnValue);
    }

    public List<T> getOrderedList(String columnName,
                                  Serializable columnValue,
                                  String orderByColumnName,
                                  boolean desc)
    {
        String hql = "from " + this.getEntityClass().getSimpleName() + " en where en." + columnName + "=? order by en." + orderByColumnName;
        if(desc)
        {
            hql += " desc";
        }
        return this.find(hql, columnValue);
    }

    public Page<T> getPagedOrderedList(String columnName,
                                       Serializable columnValue,
                                       String orderByColumnName,
                                       boolean desc,
                                       int pageNo,
                                       int pageSize)
    {
        String hql = "from " + this.getEntityClass().getSimpleName() + " en where en." + columnName + "=? order by en." + orderByColumnName;
        if(desc)
        {
            hql += " desc";
        }

        return this.pagedQuery(hql, pageNo, pageSize, columnValue);
    }

//    public Page<T> getPagedOrderedList(String columnName,
//                                       Serializable columnValue,
//                                       String searchColumnName,
//                                       Serializable searchValue,
//                                       String orderByColumnName,
//                                       boolean desc,
//                                       int pageNo,
//                                       int pageSize)
//    {
//        Map<String, Serializable> searchKV = new HashMap<String, Serializable>();
//        searchKV.put(searchColumnName, searchValue);
//        return getPagedOrderedList(columnName, columnValue, searchKV, orderByColumnName, desc, pageNo, pageSize);
//    }

    public Page<T> getPagedOrderedList(String columnName,
                                       Serializable columnValue,
//                                       Map<String, Serializable> searchKV,
                                       String searchColumnName,
                                       Serializable searchValue,
                                       String orderByColumnName,
                                       boolean desc,
                                       int pageNo,
                                       int pageSize)
    {
//        StringBuilder sb = new StringBuilder();
//        sb.append("from " + this.getEntityClass().getSimpleName() + " en where en." + columnName + "=? and (");
//        for(Map.Entry<String, Serializable> kv: searchKV.entrySet())
//        {
//            sb.append("en." + kv.getKey() + " like ? or ");
//        }
//
//        if(searchKV.size() >= 1)
//        {
//            sb.replace(sb.length()-4, sb.length(), "");
//        }
//        sb.append(") order by en." + orderByColumnName);
//
//        if(desc)
//        {
//            sb.append(" desc");
//        }
//
//
//
//        List<String> searchValueList = new ArrayList<String>();
//        searchValueList.add(columnValue.toString());
//        for(Serializable value: searchKV.values())
//        {
//            searchValueList.add("%" + value + "%");
//        }
//        return this.pagedQuery(sb.toString(), pageNo, pageSize, searchValueList.toArray(new Serializable[0]));

        String hql = "from " + this.getEntityClass().getSimpleName() + " en where en." + columnName + "=? and en." + searchColumnName + " like ? order by en." + orderByColumnName;
        if(desc)
        {
            hql += " desc";
        }

        return  this.pagedQuery(hql, pageNo, pageSize, columnValue, "%" + searchValue + "%");
    }

    public <U> List<T> getList(String columnName, List<U> columnValues)
    {
        return this.findByList("from " + getEntityClass().getSimpleName() + " en where en." + columnName + " in (:lst)", "lst", columnValues);
    }

    //保存实体
    public void persist(T entity)
    {
        getSessionFactory().getCurrentSession().persist(entity);
    }

    //更新实体
    public void update(T entity)
    {
        getSessionFactory().getCurrentSession().update(entity);
    }

    //删除实体
    public void delete(T entity)
    {
        getSessionFactory().getCurrentSession().delete(entity);
    }

    //根据ID删除实体
    public void delete(Serializable id)
    {
        getSessionFactory().getCurrentSession()
                .createQuery("delete " + this.getEntityClass().getSimpleName() + " en where en.id = :id")
                .setInteger("id", (Integer) id)
                .executeUpdate();
    }

    public void delete(String columnName, Serializable columnValue)
    {
        getSessionFactory().getCurrentSession()
                .createQuery("delete " + this.getEntityClass().getSimpleName() + " en where " + columnName + " = :otherId")
                .setInteger("otherId", (Integer) columnValue)
                .executeUpdate();
    }

    //获取所有实体
    public List<T> findAll()
    {
        return find("select en from " + this.getEntityClass().getSimpleName() + " en");
    }

    //获取实体总数
    public long findCount()
    {
        List<?> l = find("select count(*) from " + this.getEntityClass().getSimpleName());

        //返回查询得到的实体总数
        if(l != null && l.size() == 1)
        {
            return (Long)l.get(0);
        }
        return 0;
    }

    //根据HQL语句查询实体
    @SuppressWarnings("unchecked")
    protected List<T> find(String hql)
    {
        return (List<T>)getSessionFactory().getCurrentSession()
                .createQuery(hql)
                .list();
    }

    //根据带占位符参数的HQL语句查询实体
    @SuppressWarnings("unchecked")
    protected List<T> find(String hql, Object... params)
    {
        //创建查询
        Query query = getSessionFactory().getCurrentSession()
                .createQuery(hql);
        return find(query, params);
    }

    private List<T> find(Query query, Object... params)
    {
        //为包含占位符的HQL语句设置参数
        for(int i = 0, len = params.length; i< len; i++)
        {
            query.setParameter(i, params[i]);
        }
        return (List<T>)query.list();
    }

    protected <U> List<T> findByList(String hql, String paramName, List<U> list)
    {
        return this.getListQuery(hql, paramName, list).list();
    }

    private <U> Query getListQuery(String hql, String paramName, List<U> list)
    {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        return query.setParameterList(paramName, list);
    }

    /**
     * 分页查询函数，使用hql.
     *
     * @param pageNo 页号,从1开始.
     */
    public Page<T> pagedQuery(String hql, int pageNo, int pageSize, Object... values) {
        Assert.hasText(hql);
        Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
        // Count查询
        String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
        List countlist = find(countQueryString, values);
        long totalCount = (Long) countlist.get(0);

        if (totalCount < 1)
            return new Page<T>();
        // 实际查询返回分页对象
        int startIndex = Page.getStartIndexOfPage(pageNo, pageSize);
        Query query = createQuery(hql, values);
        List<T> list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

        return new Page<T>(startIndex, totalCount, pageSize, list);
    }

    /**
     * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
     * 留意可以连续设置,如下：
     * <pre>
     * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
     * </pre>
     * 调用方式如下：
     * <pre>
     *        dao.createQuery(hql)
     *        dao.createQuery(hql,arg0);
     *        dao.createQuery(hql,arg0,arg1);
     *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
     * </pre>
     *
     * @param values 可变参数.
     */
    public Query createQuery(String hql, Object... values) {
        Assert.hasText(hql);
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query;
    }

    /**
     * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
     *
     * @see #pagedQuery(String,int,int,Object[])
     */
    private static String removeSelect(String hql) {
        Assert.hasText(hql);
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
        return hql.substring(beginPos);
    }

    /**
     * 去除hql的orderby 子句，用于pagedQuery.
     *
     * @see #pagedQuery(String,int,int,Object[])
     */
    private static String removeOrders(String hql) {
        Assert.hasText(hql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }
}
