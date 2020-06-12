package xyz.fur.skeleton.services;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import xyz.fur.skeleton.entity.User;
import xyz.fur.skeleton.services.base.IBaseService;

/**
 * @author Fururur
 * @create 2019-07-17-17:34
 */
@Transactional
public interface IUserService extends IBaseService<User,Long> {
    /**
     * 年龄大于age的
     *
     * @param age
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<User> findUserByConditions(int age, int pageNo, int pageSize);

    Long countBy();
}
