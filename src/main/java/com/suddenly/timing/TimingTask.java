package com.suddenly.timing;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class TimingTask {
    // 添加定时任务
    @Scheduled(cron = "0 30 17 ? * *")  // 每天下午17:30执行
//    @Scheduled(fixedRate=5000)    指定时间间隔，例如：5秒
    private void configureTasks() {
        System.err.println("执行定时任务时间: " + LocalDateTime.now());
    }
}

