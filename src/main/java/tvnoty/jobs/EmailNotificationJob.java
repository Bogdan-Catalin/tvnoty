package tvnoty.jobs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tvnoty.services.EmailNotificationService;

@Service
public class EmailNotificationJob {
    private static final Logger LOGGER = Logger.getLogger(EmailNotificationJob.class.getName());

    @Autowired
    private EmailNotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void notifyUsersOfDailyEpisodes() {
        LOGGER.info("Executing daily episode builder and notification tasks.");
        notificationService.notifySubscribers();
    }
}
