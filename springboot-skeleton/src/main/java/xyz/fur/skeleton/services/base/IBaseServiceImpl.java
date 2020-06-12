package xyz.fur.skeleton.services.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.fur.skeleton.repo.base.IBaseRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Fururur
 * @create 2019-07-18-15:05
 */
@Slf4j
public class IBaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK> {
    @Autowired
    private IBaseRepository<T, PK> repository;

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public T saveOrUpdate(T t) {
        return repository.save(t);
    }

    @Override
    public T getById(PK id) {
        return repository.getOne(id);
    }

    @Override
    public T findById(PK id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(PK id) {
        repository.deleteById(id);
    }

    @Override
    public List<T> findByEntity(T t) {
        return repository.findByEntity(t);
    }

    @Override
    public T findOne(T t) {
        return repository.findOne(t);
    }

    @Override
    public Page<T> findByEntity(T t, Pageable pageable) {
        return findByEntity(t, pageable);
    }

}
