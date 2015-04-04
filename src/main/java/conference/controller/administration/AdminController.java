package conference.controller.administration;

import conference.controller.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("admin")
@RequestMapping("/admin")
public class AdminController extends BaseController {
    public AdminController(){
        super("administration");
    }
}
