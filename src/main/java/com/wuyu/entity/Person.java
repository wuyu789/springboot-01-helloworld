package com.wuyu.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;


/**
 * Author:      wy
 * Create Date: 2022/2/16
 * Create Time: 14:43
 * Description:
 */
@Component
@ConfigurationProperties(prefix = "person")
//@PropertySource("classpath:Properties.properties")
@Validated
@Data
public class Person {
    //    @Value("${person.name}")
    private String name;
    //    @Value("${person.age}")
    private Integer age;


    //    @Value("${person.email}")
    @Email(message = "邮箱格式错误")
//    @Email
    private String email;
}
