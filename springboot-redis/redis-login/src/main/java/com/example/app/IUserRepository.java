package com.example.app;


import com.example.app.common.repo.IBaseRepository;

/**
 * @author Fururur
 * @create 2019-12-13-16:37
 */
public interface IUserRepository extends IBaseRepository<User,Long> {
    User findUserByUsername(String username);
}