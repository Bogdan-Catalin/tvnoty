package tvnoty.jobs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tvnoty.services.EmailNotificationService;
import tvnoty.services.SeriesDataGatheringService;

@Service
public class DailyGatheringAndNotificationJob {
    private static final Logger LOGGER = Logger.getLogger(DailyGatheringAndNotificationJob.class.getName());

    @Autowired
    private SeriesDataGatheringService gatheringService;

    @Autowired
    private EmailNotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * *")
    public void gatherSeriesData() {
        // TODO: after gathering enough data, run this job weekly
        LOGGER.info("Executing scheduled data gathering task.");
        gatheringService.pullData();

        LOGGER.info("Executing daily episode builder and notification tasks.");
        notificationService.notifySubscribers();
    }
}
