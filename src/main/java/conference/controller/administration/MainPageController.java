package conference.controller.administration;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController extends AdminController {

    public MainPageController(){
        super();
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping({"/homepage", ""})
    public ModelAndView index(){
        title("Hlavní stránka");
        return getTemplate();
    }
}
