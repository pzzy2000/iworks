package cn.oxo.iworks.task.quartz.configs;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

// @Configuration
public abstract class QuartzConfig implements ApplicationListener<ContextRefreshedEvent> {

      private Logger logger = LogManager.getLogger(QuartzConfig.class);

      @Override
      public void onApplicationEvent(ContextRefreshedEvent event) {
            // log.info("任务已经启动..." + event.getSource());

      }

      // "/configs/task_quartz.properties"
      protected abstract String getConfile();

      @Bean
      public TaskJobFactory taskJobFactory(@Autowired AutowireCapableBeanFactory capableBeanFactory) {
            return new TaskJobFactory(capableBeanFactory);
      }

      @Bean
      public SchedulerFactoryBean schedulerFactoryBean(@Autowired DataSource dataSource, @Autowired TaskJobFactory taskJobFactory) throws IOException {
            // 获取配置属性
            PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
            propertiesFactoryBean.setLocation(new ClassPathResource(getConfile()));
            // 在quartz.properties中的属性被读取并注入后再初始化对象
            propertiesFactoryBean.afterPropertiesSet();
            // 创建SchedulerFactoryBean
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
            factory.setQuartzProperties(propertiesFactoryBean.getObject());
            // 使用数据源，自定义数据源
            factory.setDataSource(dataSource);
            factory.setJobFactory(taskJobFactory);
            factory.setWaitForJobsToCompleteOnShutdown(true);// 这样当spring关闭时，会等待所有已经启动的quartz
                                                             // job结束后spring才能完全shutdown。
            factory.setOverwriteExistingJobs(true);
            factory.setStartupDelay(1);
            return factory;
      }

      @Bean
      public Scheduler scheduler(@Autowired SchedulerFactoryBean schedulerFactoryBean) throws IOException {
            return schedulerFactoryBean.getScheduler();
      }

      @Bean
      public QuartzInitializerListener executorListener() {
            return new QuartzInitializerListener();
      }

}
