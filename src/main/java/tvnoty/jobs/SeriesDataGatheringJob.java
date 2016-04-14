package tvnoty.jobs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tvnoty.services.SeriesDataGatheringService;

@Service
public class SeriesDataGatheringJob {
    private static final Logger LOGGER = Logger.getLogger(EmailNotificationJob.class.getName());

    @Autowired
    private SeriesDataGatheringService gatheringService;

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void gatherSeriesData() {
        // TODO: after gathering enough data, run this job weekly
        LOGGER.info("Executing scheduled data gathering task.");
        gatheringService.pullData();
    }
}
