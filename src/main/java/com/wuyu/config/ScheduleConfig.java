package com.wuyu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Author:      wy
 * Create Date: 2022/2/20
 * Create Time: 21:59
 * Description:
 */
@Configuration
public class ScheduleConfig {

    @Scheduled(cron = "0 0/1 * * * ?")
    public void run (){
        System.out.println("111111111111111111");
    }
}
