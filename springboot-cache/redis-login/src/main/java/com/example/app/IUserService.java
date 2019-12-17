package com.example.app;

/**
 * @author Fururur
 * @create 2019-12-17-9:50
 */
public interface IUserService {
    String checkLogin(String username, String password);

    String logout(String username);
}
