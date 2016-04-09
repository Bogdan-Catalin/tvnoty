package tvnoty.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tvnoty.controllers.models.ErrorResponseEnum;
import tvnoty.core.database.repositories.SubscriberRepository;
import tvnoty.services.SeriesDataGatheringService;
import tvnoty.services.SubscriptionService;
import tvnoty.utils.TvnotyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

        final ObjectMapper om = new ObjectMapper();

        if (!isValidEmail(email)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, ErrorResponseEnum.INVALID_EMAIL);
        }

        List<String> subList = new ArrayList<>();
        try {
            subList = om.readValue(jsonSubscriptions, new TypeReference<List<String>>() {
            });
        } catch (final IOException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, ErrorResponseEnum.INVALID_SUBSCRIBE_LIST);
        }

        subscriptionService.subscribe(email, subList);
        gatheringService.pullSpecificData(new HashSet<>(subList));

        // TODO: remove this and send an OK message - keep only for testing
        response.setStatus(HttpStatus.OK.value());
        return TvnotyUtils.mapObjectToRESTResponse(om, subscriberRepository.findAll());
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public String unsubscribe(
            @RequestParam(value = "email") final String email,
            @RequestParam(value = "unsubscribe_list") final String jsonSubscriptions,
            final HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        final ObjectMapper om = new ObjectMapper();

        if (!isValidEmail(email)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, ErrorResponseEnum.INVALID_EMAIL);
        }

        List<String> subList = new ArrayList<>();
        try {
            subList = om.readValue(jsonSubscriptions, new TypeReference<List<String>>() {
            });
        } catch (final IOException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, ErrorResponseEnum.INVALID_UNSUBSCRIBE_LIST);
        }

        subscriptionService.unsubscribe(email, subList);

        // TODO: remove this and send an OK message - keep only for testing
        response.setStatus(HttpStatus.OK.value());
        return TvnotyUtils.mapObjectToRESTResponse(om, subscriberRepository.findAll());
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.PUT)
    public String putSubscribe(
            @RequestParam(value = "email") final String email,
            @RequestParam(value = "subscribe_list") final String jsonSubscriptions,
            final HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        final ObjectMapper om = new ObjectMapper();

        if (!isValidEmail(email)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, ErrorResponseEnum.INVALID_EMAIL);
        }

        List<String> subList = new ArrayList<>();
        try {
            subList = om.readValue(jsonSubscriptions, new TypeReference<List<String>>() {
            });
        } catch (final IOException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, ErrorResponseEnum.INVALID_SUBSCRIBE_LIST);
        }

        subscriptionService.putSubscribe(email, subList);
        // TODO: remove this and send an OK message - keep only for testing
        response.setStatus(HttpStatus.OK.value());
        return TvnotyUtils.mapObjectToRESTResponse(om, subscriberRepository.findAll());
    }

    private boolean isValidEmail(final String email) {
        final EmailValidator ev = EmailValidator.getInstance();
        return ev.isValid(email);
    }
}
