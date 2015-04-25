package conference.controller.user;

import conference.controller.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@PreAuthorize("user")
@RequestMapping({"/user", "/search"})
public class UserController extends BaseController{

    public UserController(){
        super("user");
    }
}