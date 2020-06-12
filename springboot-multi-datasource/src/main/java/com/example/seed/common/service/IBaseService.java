package com.example.seed.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Fururur
 * @create 2019-07-18-15:02
 */
@NoRepositoryBean
public interface IBaseService<T,PK extends Serializable> {

    /**
     * 一组实现相同的方法，突出语义化
     * @param t
     * @return
     */
    T save(T t);

    T saveOrUpdate(T t);

    /**
     * 查询无结果会 throw EntityNotFoundException
     * @param id
     * @return
     */
    T getById(PK id);

    /**
     * 查询无结果直接返回 null
     * @param id
     * @return
     */
    T findById(PK id);

    void deleteById(PK id);

    List<T> findByEntity(T t);

    T findOne(T t);

    Page<T> findByEntity(T t, Pageable pageable);
}
