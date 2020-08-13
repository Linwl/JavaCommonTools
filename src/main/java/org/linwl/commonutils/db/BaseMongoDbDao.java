package org.linwl.commonutils.db;

import cn.hutool.core.bean.BeanUtil;
import org.linwl.commonutils.models.BaseMongoDbEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @program: JavaCommonTools
 * @description:
 * @author: linwl
 * @create: 2020-08-13 14:36
 **/
public abstract class BaseMongoDbDao<T extends BaseMongoDbEntity> {

    private static final Logger log = LoggerFactory.getLogger(BaseMongoDbDao.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 反射获取泛型类型
     *
     * @return
     */
    protected abstract Class<T> getEntityClass();

    /**
     * 保存
     *
     * @param t
     * @param collectionName 集合名称
     */
    public void save(T t, String collectionName) {
        log.info("-------------->MongoDB save start");
        this.mongoTemplate.save(t, collectionName);
    }

    /**
     * 保存
     *
     * @param t
     */
    public void save(T t) {
        log.info("-------------->MongoDB save start");
        this.mongoTemplate.save(t);
    }

    /**
     * 批量保存
     *
     * @param objectsToSave
     */
    public Collection<T> batchSave(Collection<? extends T> objectsToSave) {
        log.info("-------------->MongoDB batch save start");
        return this.mongoTemplate.insert(objectsToSave,this.getEntityClass());
    }

    /**
     * 批量保存
     * @param objectsToSave
     * @param collectionName
     */
    public Collection<T> batchSave(Collection<? extends T> objectsToSave, String collectionName) {
        log.info("-------------->MongoDB batch save start");
        return this.mongoTemplate.insert(objectsToSave,collectionName);
    }

    /**
     * * 根据id从几何中查询对象
     *
     * @param id
     * @return
     */
    public T queryById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        log.info("-------------->MongoDB find start");
        return this.mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 根据id从几何中查询对象
     * @param id
     * @param collectionName
     * @return
     */
    public T queryById(String id,String collectionName) {
        Query query = new Query(Criteria.where("_id").is(id));
        log.info("-------------->MongoDB find start");
        return this.mongoTemplate.findOne(query, this.getEntityClass(),collectionName);
    }

    /**
     * 根据条件查询集合
     *
     * @param object
     * @return
     */
    public List<T> queryList(T object) {
        Query query = getQueryByObject(object);
        log.info("-------------->MongoDB find start");
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 根据条件查询集合
     * @param object
     * @param collectionName
     * @return
     */
    public List<T> queryList(T object,String collectionName) {
        Query query = getQueryByObject(object);
        log.info("-------------->MongoDB find start");
        return mongoTemplate.find(query, this.getEntityClass(),collectionName);
    }

    /**
     * 根据条件查询只返回一个文档
     *
     * @param object
     * @return
     */
    public T queryOne(T object) {
        Query query = getQueryByObject(object);
        log.info("-------------->MongoDB find start");
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 根据条件查询只返回一个文档
     * @param object
     * @param collectionName
     * @return
     */
    public T queryOne(T object,String collectionName) {
        Query query = getQueryByObject(object);
        log.info("-------------->MongoDB find start");
        return mongoTemplate.findOne(query, this.getEntityClass(),collectionName);
    }

    /**
     * * 根据条件分页查询
     *
     * @param object
     * @param start 查询起始值
     * @param size 查询大小
     * @return
     */
    public List<T> getPage(T object, int start, int size) {
        Query query = getQueryByObject(object);
        if(start >0)
        {
            start --;
        }
        query.skip(start);
        query.limit(size);
        log.info("-------------->MongoDB queryPage start");
        return this.mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 根据条件分页查询
     * @param object
     * @param start
     * @param size
     * @param collectionName
     * @return
     */
    public List<T> getPage(T object, int start, int size,String collectionName) {
        Query query = getQueryByObject(object);
        if(start >0)
        {
            start --;
        }
        query.skip(start);
        query.limit(size);
        log.info("-------------->MongoDB queryPage start");
        return this.mongoTemplate.find(query, this.getEntityClass(),collectionName);
    }

    /**
     * * 根据条件查询库中符合条件的记录数量
     *
     * @param object
     * @return
     */
    public Long getCount(T object) {
        Query query = getQueryByObject(object);
        log.info("-------------->MongoDB Count start");
        return this.mongoTemplate.count(query, this.getEntityClass());
    }

    /**
     * 根据条件查询库中符合条件的记录数量
     * @param object
     * @param collectionName
     * @return
     */
    public Long getCount(T object,String collectionName) {
        Query query = getQueryByObject(object);
        log.info("-------------->MongoDB Count start");
        return this.mongoTemplate.count(query, this.getEntityClass(),collectionName);
    }

    /*MongoDB中更新操作分为三种
     * 1：updateFirst     修改第一条
     * 2：updateMulti     修改所有匹配的记录
     * 3：upsert  修改时如果不存在则进行添加操作
     * */

    /**
     * * 删除对象
     *
     * @param t
     * @return
     */
    public int delete(T t) {
        log.info("-------------->MongoDB delete start");
        return (int) this.mongoTemplate.remove(t).getDeletedCount();
    }

    /**
     * 删除对象
     *
     * @param t
     * @param collectionName
     * @return
     */
    public int delete(T t, String collectionName) {
        log.info("-------------->MongoDB delete start");
        return (int) this.mongoTemplate.remove(t, collectionName).getDeletedCount();
    }

    /**
     * 根据id列表批量删除
     * @param ids
     * @return
     */
    public int delete(List<String> ids) {
        Criteria criteria = Criteria.where("_id").in(ids);
        Query query = new Query(criteria);
        return (int) this.mongoTemplate.remove(query, this.getEntityClass()).getDeletedCount();
    }

    /**
     * 根据id列表批量删除
     * @param ids
     * @param collectionName
     * @return
     */
    public int delete(List<String> ids,String collectionName) {
        Criteria criteria = Criteria.where("_id").in(ids);
        Query query = new Query(criteria);
        return (int) this.mongoTemplate.remove(query, this.getEntityClass(),collectionName).getDeletedCount();
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    public void deleteById(String id) {
        Criteria criteria = Criteria.where("_id").is(id);
        if (null != criteria) {
            Query query = new Query(criteria);
            T obj = this.mongoTemplate.findOne(query, this.getEntityClass());
            log.info("-------------->MongoDB deleteById start");
            if (obj != null) {
                this.delete(obj);
            }
        }
    }

    /**
     * 根据id删除
     *
     * @param id
     * @param collectionName 集合名称
     */
    public void deleteById(String id, String collectionName) {
        Criteria criteria = Criteria.where("_id").is(id);
        if (null != criteria) {
            Query query = new Query(criteria);
            T obj = this.mongoTemplate.findOne(query, this.getEntityClass());
            log.info("-------------->MongoDB deleteById start");
            if (obj != null) {
                this.delete(obj, collectionName);
            }
        }
    }

    /**
     * 修改匹配到的第一条记录
     *
     * @param srcObj
     * @param targetObj
     */
    public void updateFirst(T srcObj, T targetObj) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        log.info("-------------->MongoDB updateFirst start");
        this.mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }

    /**
     * 修改匹配到的第一条记录
     *
     * @param srcObj
     * @param targetObj
     * @param collectionName 集合名称
     */
    public void updateFirst(T srcObj, T targetObj, String collectionName) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        log.info("-------------->MongoDB updateFirst start");
        this.mongoTemplate.updateFirst(query, update, collectionName);
    }

    /**
     * * 修改匹配到的所有记录
     *
     * @param srcObj
     * @param targetObj
     */
    public void updateMulti(T srcObj, T targetObj) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        log.info("-------------->MongoDB updateFirst start");
        this.mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    /**
     * 修改匹配到的所有记录
     *
     * @param srcObj
     * @param targetObj
     * @param collectionName 集合名称
     */
    public void updateMulti(T srcObj, T targetObj, String collectionName) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        log.info("-------------->MongoDB updateFirst start");
        this.mongoTemplate.updateMulti(query, update, collectionName);
    }

    /**
     * * 修改匹配到的记录，若不存在该记录则进行添加
     *
     * @param srcObj
     * @param targetObj
     */
    public void updateInsert(T srcObj, T targetObj) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        log.info("-------------->MongoDB updateInsert start");
        this.mongoTemplate.upsert(query, update, this.getEntityClass());
    }

    /**
     * 修改匹配到的记录，若不存在该记录则进行添加
     *
     * @param srcObj
     * @param targetObj
     * @param collectionName 集合名字
     */
    public void updateInsert(T srcObj, T targetObj, String collectionName) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        log.info("-------------->MongoDB updateInsert start");
        this.mongoTemplate.upsert(query, update, collectionName);
    }

    /**
     * 将查询条件对象转换为query
     *
     * @param object
     * @return
     * @author Jason
     */
    private Query getQueryByObject(T object) {
        Query query = new Query();
        Map<String, Object> dataMap = BeanUtil.beanToMap(object, false, true);
        Criteria criteria = new Criteria();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        return query;
    }

    /**
     * 将查询条件对象转换为update
     *
     * @param object
     * @return
     * @author Jason
     */
    private Update getUpdateByObject(T object) {
        Update update = new Update();
        Map<String, Object> dataMap = BeanUtil.beanToMap(object, false, true);
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            update.set(entry.getKey(), entry.getValue());
        }
        return update;
    }
}
