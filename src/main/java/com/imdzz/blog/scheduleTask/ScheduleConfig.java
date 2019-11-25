//package com.imdzz.blog.scheduleTask;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//
//import java.util.concurrent.Executors;
//
///**
// * 定时任务执行线程池
// * @author imdzz
// * @version 1.0
// * @date 2019/10/16 10:28
// */
//@Configuration
//public class ScheduleConfig implements SchedulingConfigurer {
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar){
//        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(3));
//    }
//}
