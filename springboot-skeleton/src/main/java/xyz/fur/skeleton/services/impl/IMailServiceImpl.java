package xyz.fur.skeleton.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import xyz.fur.skeleton.services.IMailService;

import java.time.LocalDate;

/**
 * @author Fururur
 * @date 2020/5/12-9:12
 */
@Slf4j
@Service
public class IMailServiceImpl implements IMailService {

    private final JavaMailSender sender;

    public IMailServiceImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public void send(String to, String subject, String content) {
        log.info("send mail to [" + to + "] with content :" + content);
        try {
            //建立邮件消息
            SimpleMailMessage mainMessage = new SimpleMailMessage();
            //发送者
            mainMessage.setFrom("AI 训练平台<arcai@arcsoft.com.cn>");
            //接收者
            mainMessage.setTo(to + "@arcsoft.com.cn");
            //发送的标题
            mainMessage.setSubject(subject);
            //发送的内容
            mainMessage.setText("Hi " + to + ",\n\n" + content + "\n\nArcAI\n" + LocalDate.now().toString());
            sender.send(mainMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
