package conference.controller.administration;

import conference.model.entity.Conference;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConferenceController extends AdminController{
    private static final Logger logger = Logger.getLogger(ConferenceController.class);
    public ConferenceController(){
        super();
    }

    @RequestMapping(value="/conference/add", method=RequestMethod.GET)
    public ModelAndView initForm(){
        addObject("conference", new Conference());
        setView("add");
        return getTemplate();
    }

    @RequestMapping(value="/conference/add", method=RequestMethod.POST)
    public ModelAndView onSubmit(Conference conference, BindingResult result){
        addObject("conference", conference);
        if(result.hasErrors()){
            setView("add");
        }else setView("success");
        return getTemplate();
    }

 /*   @RequestMapping(value="/conference/add", method=RequestMethod.POST)
    public String onSubmit(Model model, Conference conference, BindingResult result){
        model.addAttribute("conference", conference);
        String returnVal = "success";
        if(result.hasErrors()) {
            returnVal = "form";
        } else {
            model.addAttribute("form", conference);
        }
        return returnVal;
    }
*/
   /* @RequestMapping(value="/conference/add", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("conference")Conference conference, BindingResult result){
        logger.info("Jmeno: "+conference.getName());

        title("Přidat konferenci");
        setView("add");
        return getTemplate();
    }*/
}
