package conference.controller;

import conference.controller.AdminController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

@Controller
public class MainPageController extends AdminController {

    public MainPageController(){
        super();
    }

    @RequestMapping("/admin")
    public ModelAndView index(){
        title("Administrace");
        return getTemplate();
    }
}
