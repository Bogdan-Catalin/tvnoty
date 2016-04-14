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
import tvnoty.controllers.models.RESTResponseEnum;
import tvnoty.core.database.entities.Subscriber;
import tvnoty.core.database.repositories.SubscriberRepository;
import tvnoty.services.SubscriptionService;
import tvnoty.utils.TvnotyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SubscribeController {
    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    SubscriptionService subscriptionService;

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public String subscribe(
            @RequestParam(value = "email") final String email,
            @RequestParam(value = "subscribe_list") final String jsonSubscriptions,
            final HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        final ObjectMapper om = new ObjectMapper();

        if (!isValidEmail(email)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, RESTResponseEnum.INVALID_EMAIL);
        }

        List<String> subList = new ArrayList<>();
        try {
            subList = om.readValue(jsonSubscriptions.trim(), new TypeReference<List<String>>() {
            });
        } catch (final IOException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, RESTResponseEnum.INVALID_SUBSCRIBE_LIST);
        }

        subscriptionService.subscribe(email, subList);

        response.setStatus(HttpStatus.OK.value());
        return TvnotyUtils.mapObjectToRESTResponse(om, RESTResponseEnum.SUBSCRIBE_OK);
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
            return TvnotyUtils.mapObjectToRESTResponse(om, RESTResponseEnum.INVALID_EMAIL);
        }

        List<String> subList = new ArrayList<>();
        try {
            subList = om.readValue(jsonSubscriptions.trim(), new TypeReference<List<String>>() {
            });
        } catch (final IOException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, RESTResponseEnum.INVALID_SUBSCRIBE_LIST);
        }

        subscriptionService.putSubscribe(email, subList);

        response.setStatus(HttpStatus.OK.value());
        return TvnotyUtils.mapObjectToRESTResponse(om, RESTResponseEnum.SUBSCRIBE_OK);
    }

    @RequestMapping(value = "/get_subscriptions", method = RequestMethod.GET)
    public String getSubscriptions(
            @RequestParam(value = "email") final String email,
            final HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        final ObjectMapper om = new ObjectMapper();

        if (!isValidEmail(email)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, RESTResponseEnum.INVALID_EMAIL);
        }

        final Subscriber subscriber = subscriberRepository.findByEmail(email);
        if (subscriber == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, RESTResponseEnum.USER_NOT_FOUND);
        } else {
            response.setStatus(HttpStatus.OK.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, subscriber.getSubscriptions());
        }
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
            return TvnotyUtils.mapObjectToRESTResponse(om, RESTResponseEnum.INVALID_EMAIL);
        }

        List<String> subList = new ArrayList<>();
        try {
            subList = om.readValue(jsonSubscriptions.trim(), new TypeReference<List<String>>() {
            });
        } catch (final IOException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return TvnotyUtils.mapObjectToRESTResponse(om, RESTResponseEnum.INVALID_UNSUBSCRIBE_LIST);
        }

        subscriptionService.unsubscribe(email, subList);

        response.setStatus(HttpStatus.OK.value());
        return TvnotyUtils.mapObjectToRESTResponse(om, RESTResponseEnum.UNSUBSCRIBE_OK);
    }

    private boolean isValidEmail(final String email) {
        final EmailValidator ev = EmailValidator.getInstance();
        return ev.isValid(email);
    }
}
