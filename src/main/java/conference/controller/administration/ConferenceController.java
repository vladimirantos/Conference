package conference.controller.administration;

import conference.configuration.FlashMessage;
import conference.model.FileStorage;
import conference.model.Log;
import conference.model.entity.Conference;
import conference.model.repository.ConferenceRepository;
import conference.validator.ConferenceFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Controller
public class ConferenceController extends AdminController{

    private Map<Integer, String> months;

    @Autowired
    @Qualifier("conferenceFormValidator")
    private ConferenceFormValidator validator;

    @Autowired
    private ConferenceRepository conferenceRepository;

    public ConferenceController() {
        super();
        this.months = new LinkedHashMap<Integer, String>();
        String[] m = new String[]{"Leden", "Únor", "Březen", "Duben", "Květen", "Červen", "Červenec", "Srpen",
                "Září", "Říjen", "Listopad", "Prosinec"};
        for (int i = 0; i < m.length; i++) {
            this.months.put(i + 1, m[i]);
        }
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value="/conference/add", method=RequestMethod.GET)
    public ModelAndView initForm(){
        title("Vytvoření konference");
        addObject("conference", new Conference()).addObject("months", months);
        addObject("conferences", conferenceRepository.findAll());
        setView("add");
        return getTemplate();
    }

    @RequestMapping(value="/conference/add", method=RequestMethod.POST)
    public ModelAndView onSubmit(@Validated Conference conference, BindingResult result, RedirectAttributes redirectAttributes){
        addObject("conference", conference);
        if(result.hasErrors()){
            flashMessage("Nepodařilo se odeslat formulář", FlashMessage.ERROR);
            setView("add");
        } else {
            try{
                conferenceRepository.insert(conference);
                conference.createDir();
                flashMessage("Konference byla úspěšně přidána");
                return redirect("this");
            }catch(RuntimeException ex){
                flashMessage(ex.getMessage(), FlashMessage.ERROR);
            }
        }
        return getTemplate();
    }
}
