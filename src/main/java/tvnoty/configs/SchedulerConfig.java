package tvnoty.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "tvnoty/jobs")
public class SchedulerConfig implements SchedulingConfigurer {
    @Bean(destroyMethod = "shutdown")
    Executor taskExecutors() {
        return Executors.newScheduledThreadPool(10);
    }

    @Override
    public void configureTasks(final ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutors());
    }
}
