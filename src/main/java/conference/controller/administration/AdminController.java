package conference.controller.administration;

import conference.controller.BaseController;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

@Controller
@PreAuthorize("admin")
@RequestMapping("/admin")
public class AdminController extends BaseController {
    public AdminController(){
        super("administration");
    }
}
