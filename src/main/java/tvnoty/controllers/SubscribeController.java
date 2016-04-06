package tvnoty.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tvnoty.core.database.repositories.SubscriberRepository;
import tvnoty.services.SeriesDataGatheringService;
import tvnoty.services.SubscriptionService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class SubscribeController {
    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    SeriesDataGatheringService gatheringService;

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public String subscribe(
            @RequestParam(value = "email") final String email,
            @RequestParam(value = "subscribe_list") final String jsonSubscriptions,
            final HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        if (!isValidEmail(email)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "Email is not valid";
        }

        final ObjectMapper om = new ObjectMapper();
        List<String> subList = new ArrayList<>();
        try {
            subList = om.readValue(jsonSubscriptions, new TypeReference<List<String>>() {
            });
        } catch (final IOException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "Badly formed subscription list";
        }

        subscriptionService.subscribe(email, subList);
        gatheringService.pullSpecificData(new HashSet<>(subList));

        try {
            response.setStatus(HttpStatus.OK.value());
            return om.writeValueAsString(subscriberRepository.findAll());
        } catch (final JsonProcessingException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "Jackson is having a bad day and I am too :( sorry";
        }
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public String unsubscribe(
            @RequestParam(value = "email") final String email,
            @RequestParam(value = "unsubscribe_list") final String jsonSubscriptions,
            final HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        if (!isValidEmail(email)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "Email is not valid";
        }

        final ObjectMapper om = new ObjectMapper();
        List<String> subList = new ArrayList<>();
        try {
            subList = om.readValue(jsonSubscriptions, new TypeReference<List<String>>() {
            });
        } catch (final IOException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "Badly formed unsubscription list";
        }

        subscriptionService.unsubscribe(email, subList);

        try {
            response.setStatus(HttpStatus.OK.value());
            return om.writeValueAsString(subscriberRepository.findAll());
        } catch (final JsonProcessingException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "Jackson is having a bad day and I am too :( sorry";
        }
    }

    private boolean isValidEmail(final String email) {
        final Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        final Matcher m = p.matcher(email);
        final boolean matchFound = m.matches();
        if (matchFound) {
            return true;
        } else {
            return false;
        }
    }
}
