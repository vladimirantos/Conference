package conference.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExportController extends UserController{

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView export(){
        return title("Export článku").getTemplate();
    }
}
