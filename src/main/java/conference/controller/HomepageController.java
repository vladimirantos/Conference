package conference.controller;

import conference.configuration.Constants;
import conference.exceptions.http.ForbiddenException;
import conference.exceptions.http.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomepageController extends BaseController{

    private static final Logger logger = Logger.getLogger(HomepageController.class);

    public HomepageController(){
        title("Přihlášení do aplikace");
        setView("homepage");
        addObject("pokus", logger.isInfoEnabled());
    }

    @RequestMapping("/")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        logger.error("CHYBA PICO");
        logger.info("INFO PICO");
        if (error != null) {
            addObject("error", "Invalid email or password!");
        }

        if (logout != null) {
            addObject("msg", "You've been logged out successfully.");
        }

        return setView("index").getTemplate();
    }

    @RequestMapping("/login/error")
    public ModelAndView error(){
        throw new ForbiddenException("Chybné přihlašovací údaje");
    }
}
