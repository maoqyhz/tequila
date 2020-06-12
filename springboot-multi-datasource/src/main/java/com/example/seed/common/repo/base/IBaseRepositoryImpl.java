package com.example.seed.common.repo.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * @author Fururur
 * @create 2019-07-18-16:03
 */
@Transactional
public class IBaseRepositoryImpl<T, PK extends Serializable> extends SimpleJpaRepository<T, PK> implements IBaseRepository<T, PK> {

    private EntityManager em;

    // 复杂sql也可直接使用JdbcTemplate进行操作
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public IBaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getObjListBySql(String sql) {
        Query query = em.createNativeQuery(sql);
        return (List<Object[]>) query.getResultList();
    }

    @Override
    public T findOne(T t) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues();
        Example<T> et = Example.of(t, matcher);
        return super.findOne(et).orElse(null);
    }

    @Override
    public List<T> findByEntity(T t) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues();
        Example<T> et = Example.of(t, matcher);
        return super.findAll(et);
    }

    @Override
    public Page<T> findByEntity(T t, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues();
        Example<T> et = Example.of(t, matcher);
        return super.findAll(et, pageable);
    }

}
