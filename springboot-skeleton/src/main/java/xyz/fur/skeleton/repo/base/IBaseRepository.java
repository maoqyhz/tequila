package xyz.fur.skeleton.repo.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * IBaseRepository最主要的目的还是在于同时实现JpaRepository和JpaSpecificationExecutor，使得后面的repo无需再一个个继承
 * 默认是不需要实现的，直接通过JpaRepositoryFactoryBean注入实例。
 * 但是有需要也可添加自己的实现，可具体看自己的业务逻辑去封装。
 * @author Fururur
 * @create 2019-07-18-10:30
 */
@NoRepositoryBean
public interface IBaseRepository<T,PK extends Serializable> extends JpaRepository<T, PK>, JpaSpecificationExecutor<T> {
    /**
     * 原生sql返回结果
     * @param sql
     * @return
     */
    List<Object[]> getObjListBySql(String sql);

    /**
     * 根据对象查找，范围列表
     * @param t
     * @return
     */
    List<T> findByEntity(T t);

    /**
     * 根据对象查找，返回唯一结果
     * @param t
     * @return
     */
    T findOne(T t);

    Page<T> findByEntity(T t, Pageable pageable);

}
