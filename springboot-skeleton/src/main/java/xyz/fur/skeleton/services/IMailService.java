package xyz.fur.skeleton.services;

/**
 * @author Fururur
 * @date 2020/5/12-9:10
 */
public interface IMailService {

    void send(String to, String subject, String content);
}
