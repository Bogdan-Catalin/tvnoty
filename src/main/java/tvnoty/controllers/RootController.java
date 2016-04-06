package tvnoty.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class RootController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String plaintext(final HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        return "Hi.";
    }
}
