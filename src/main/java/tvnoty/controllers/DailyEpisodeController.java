package tvnoty.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tvnoty.core.database.repositories.DailyEpisodeRepository;
import tvnoty.utils.TvnotyUtils;

@RestController
public class DailyEpisodeController {
    @Autowired
    private DailyEpisodeRepository repository;

    @RequestMapping(value = "/daily_episodes", method = RequestMethod.GET)
    public String getDailyEpisodes() {
        return TvnotyUtils.mapObjectToRESTResponse(new ObjectMapper(), repository.findAll());
    }
}
