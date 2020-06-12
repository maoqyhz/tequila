package xyz.fur.skeleton.repo.jpa;

import xyz.fur.skeleton.entity.User;
import xyz.fur.skeleton.repo.base.IBaseRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Fururur
 * @create 2019-07-16-17:15
 */
public interface IUserRepository extends IBaseRepository<User,Long> {
    List<User> findByName(String name);
    List<User> findByAgeAndCreateTimeBetween(Integer age, LocalDateTime createTime, LocalDateTime createTime2);
}
