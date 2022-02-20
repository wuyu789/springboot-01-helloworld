package com.wuyu;

import com.wuyu.entity.Person;
import com.wuyu.entity.User;
import com.wuyu.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;

@SpringBootTest
class Springboot01HelloworldApplicationTests {

    @Autowired
    private Person person;

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
        System.out.println(person);
    }

    @Test
    public void testRedis() {
//        redisTemplate.opsForValue().set("myKey","myValue");
//        redisTemplate.opsForValue().set("user",new User("wuyu","123"));
//        System.out.println(redisTemplate.opsForValue().get("user"));
        redisUtil.set("user",new User("lisi","123456"));
        System.out.println(redisUtil.get("user"));
    }

    @Test
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("hello!!!你好呀");
        message.setText("my name is wuyu");
        message.setFrom("wuyu13276284262@163.com");
        message.setTo("1308638159@qq.com");
        javaMailSender.send(message);
    }

    @Test
    public void sendMimeMail2() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 组装
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // 正文
        mimeMessageHelper.setSubject("【大陆集团康迪泰克】请查收您的专属手册");
        mimeMessageHelper.setText("<h3 style='color:red'>xxx这是女的xxxx<h3><br/>", true);

        // 附件
        mimeMessageHelper.addAttachment("1.jpg", new File("C:\\Users\\wuyu\\Desktop\\1.jpg"));
        mimeMessageHelper.addAttachment("2.jpg", new File("C:\\Users\\wuyu\\Desktop\\1.jpg"));

        mimeMessage.setFrom("wuyu13276284262@163.com");
        mimeMessageHelper.setTo("1308638159@qq.com");

        javaMailSender.send(mimeMessage);
    }

    @Test
    public void sendMimeMail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setSubject("【大陆集团康迪泰克】请查收您的专属手册");
        mimeMessage.setFrom("wuyu13276284262@163.com");
        mimeMessage.setRecipients(Message.RecipientType.TO, new InternetAddress().parse("1308638159@qq.com"));

        // 正文
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("xxx这是女的xxxx<br/><img src='cid:1.jpg'>啦啦啦", "text/html;charset=UTF-8");

        // 图片
        MimeBodyPart image = new MimeBodyPart();
        image.setDataHandler(new DataHandler(new FileDataSource("C:\\Users\\wuyu\\Desktop\\1.jpg")));
        image.setContentID("1.jpg");

        // 附件
        MimeBodyPart attach = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("C:\\Users\\wuyu\\Desktop\\1.jpg"));
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName());

        //容器1
        // 描述关系:正文和图片
        MimeMultipart mp1 = new MimeMultipart();
        mp1.addBodyPart(text);
        mp1.addBodyPart(image);
        mp1.setSubType("related");

        MimeBodyPart content = new MimeBodyPart();
        content.setContent(mp1);

        //容器2
        // 描述关系:正文和附件
        MimeMultipart mp2 = new MimeMultipart();
        mp2.addBodyPart(attach);       //附件1bodypart
        //mp2.addBodyPart(attach2);	   //附件2borypart
        mp2.addBodyPart(content);      //正文加图片borypart
        mp2.setSubType("mixed");

        mimeMessage.setContent(mp2);

        javaMailSender.send(mimeMessage);
    }

}
