package com.wuyu;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.wuyu.entity.Person;
import com.wuyu.entity.Student;
import com.wuyu.entity.User;
import com.wuyu.utils.PropertiesUtil;
import com.wuyu.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
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
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        redisUtil.set("user", new User("lisi", "123456"));
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

    @Test
    public void testRegex() {
        String qq = "1308638159";
        boolean matches = qq.matches("[1-9]\\d{5,19}");
        System.out.println(matches);
    }

    @Test
    public void testRegex2() {
        System.out.println("18896502614".matches("1[3-9]\\d{9}"));
    }

    @Test
    public void run() {
//        Calendar calendar = Calendar.getInstance();
//        System.out.println(calendar);
//
//        Date date = calendar.getTime();
        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.systemDefault());
        System.out.println(zonedDateTime);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format1 = format.format(zonedDateTime);
        System.out.println(format1);

        System.out.println("----------------------");
        LocalDate date = LocalDate.of(2022, 8, 24);
        System.out.println(date);
        System.out.println(date.getDayOfWeek().getValue());

        System.out.println("=========================");

        LocalDate now = LocalDate.now();
        LocalDate localDate = LocalDate.of(1994, 10, 14);
        MonthDay monthDay = MonthDay.of(localDate.getMonthValue(), localDate.getDayOfMonth());
        MonthDay monthDay1 = MonthDay.of(now.getMonthValue(), now.getDayOfMonth());
        System.out.println(monthDay.equals(monthDay1));
    }

    @Test
    public void run2() {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(2021, 8, 24);
        Period period = Period.between(date, now);
        System.out.println(period);
        System.out.println(period.getYears() + "," + period.getMonths() + "," + period.getDays());
        System.out.println(period.toTotalMonths());
    }

    @Test
    public void run3() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date = LocalDateTime.of(2021, 8, 24, 0, 0, 0);
        Duration duration = Duration.between(date, now);
        System.out.println(duration.toDays());
        System.out.println(duration.toHours());
        System.out.println(duration.toMinutes());
        System.out.println(duration.toMillis());
        System.out.println(duration.toNanos());
    }

    @Test
    public void run4() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date = LocalDateTime.of(2021, 8, 24, 0, 0, 0);
        System.out.println("相差的年数：" + ChronoUnit.YEARS.between(date, now));
        System.out.println("相差的月数：" + ChronoUnit.MONTHS.between(date, now));
        System.out.println("相差的周数：" + ChronoUnit.WEEKS.between(date, now));
        System.out.println("相差的日数：" + ChronoUnit.DAYS.between(date, now));
        System.out.println("相差的小时数：" + ChronoUnit.HOURS.between(date, now));
        System.out.println("相差的分钟数：" + ChronoUnit.MINUTES.between(date, now));
        System.out.println("相差的秒数：" + ChronoUnit.SECONDS.between(date, now));
    }

    @Test
    public void run5() {
        // Lambda表达式
        /**
         * Lambda表达式的省略写法:
         * 1.参数类型可以省略不写
         * 2.如果只有一个参数，参数类型可以省略，同时()也可以省略
         * 3.如果Lambda表达式的方法体只有一行，大括号，分号，return都可以省略不写，需要同事省略。
         */
        Integer[] arr = {1, 4, 3, 2, 6, 7, 5, 9, 8};
//        Arrays.sort(arr, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1 - o2;
//            }
//        });
//        Arrays.sort(arr, (Integer o1, Integer o2) -> {
//            return o1 - o2;
//        });
        Arrays.sort(arr, (o1, o2) -> o1 - o2);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.asList(arr));
    }

    @Test
    public void run6() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list1 = list.stream().filter(item -> item.equals(3)).collect(Collectors.toList());
        System.out.println(list1);
    }

    @Test
    public void run7() {
        String[] arr = {"aaa", "a", "aa", "aaaa"};
        Arrays.sort(arr, (o1, o2) -> o1.length() - o2.length());
        System.out.println(Arrays.asList(arr));
    }

    @Test
    public void run8() throws IOException, SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        System.out.println(PropertiesUtil.getPro());
        dataSource.setProperties(PropertiesUtil.getPro());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    @Test
    public void run9() throws Exception {
        DataSource dataSource = DruidDataSourceFactory.createDataSource(PropertiesUtil.getPro());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void run10() {
        Student.show();
    }

    @Test
    public void run11() {
        Logger logger = LoggerFactory.getLogger(Springboot01HelloworldApplicationTests.class);
        logger.info("hello");
    }
}
