package tvnoty.services;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tvnoty.core.database.entities.DailyEpisode;
import tvnoty.core.database.entities.Subscriber;
import tvnoty.core.database.repositories.DailyEpisodeRepository;
import tvnoty.core.database.repositories.SubscriberRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailNotificationService {
    private static final Logger LOGGER = Logger.getLogger(EmailNotificationService.class.getName());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private DailyEpisodesBuilderService dailyEpisodesBuilderService;

    @Autowired
    private DailyEpisodeRepository dailyEpisodeRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    public void notifySubscribers() {
        dailyEpisodesBuilderService.buildDailyEpisodeList();

        for (final Subscriber subscriber : subscriberRepository.findAll()) {
            final List<DailyEpisode> toWatch = new ArrayList<>();
            for (final DailyEpisode de : dailyEpisodeRepository.findAll()) {
                if (subscriber.getSubscriptions().contains(de.getSeriesImdbId())) {
                    toWatch.add(de);
                }
            }
            if (!toWatch.isEmpty()) {
                try {
                    sendEmail(subscriber, toWatch);
                } catch (final MessagingException e) {
                    LOGGER.error("Could not send email to " + subscriber.getEmail());
                }
            }
        }
        LOGGER.info("Finished sending email notifications.");
    }

    private void sendEmail(final Subscriber to, final List<DailyEpisode> episodes) throws MessagingException {
        final MimeMessage mail = mailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(to.getEmail());
        helper.setSubject("[tvnoty] New episodes available for " + getTommorrowsDate());
        final StringBuilder body = new StringBuilder();
        // TODO: use email template for body
        for (final DailyEpisode de : episodes) {

            body.append("S");
            if (de.getSeason().toString().length() == 1) {
                body.append("0");
            }
            body.append(de.getSeason() + "E");
            if (de.getEpisode().toString().length() == 1) {
                body.append("0");
            }
            body.append(de.getEpisode() + " now airing for " + de.getSeriesName() + "<br>");
        }

        mail.setContent(body.toString(), "text/html");
        mailSender.send(mail);
    }

    public String getTommorrowsDate() {
        final DateTime tomorrow = new DateTime();
        final DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MMM-yyyy");
        return tomorrow.withTimeAtStartOfDay().plusDays(1).toString(fmt).toUpperCase();
    }
}
