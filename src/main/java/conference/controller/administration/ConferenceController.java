package conference.controller.administration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConferenceController extends AdminController{

    public ConferenceController(){
        super();
    }

    @RequestMapping(value="/conference/add")
    @ResponseBody
    public ModelAndView add(){
        title("Přidat konferenci");
        setView("add");
        return getTemplate();
    }
}
