package conference.controller.administration;

import conference.configuration.FlashMessage;
import conference.model.StringEscapeEditor;
import conference.model.entity.Conference;
import conference.model.repository.ConferenceRepository;
import conference.model.repository.IConferenceRepository;
import conference.validator.ConferenceFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class ConferenceController extends AdminController {

    private Map<Integer, String> months;

    @Autowired
    @Qualifier("conferenceFormValidator")
    private ConferenceFormValidator validator;

    @Autowired
    private IConferenceRepository conferenceRepository;

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
        binder.registerCustomEditor(String.class,
                new StringEscapeEditor(true, true, false));
    }

    @RequestMapping(value="/conference/add", method=RequestMethod.GET)
    public ModelAndView initForm(){
        setView("add");
        title("Vytvoření konference");
        addObject("conference", new Conference()).addObject("months", months);
        addObject("conferences", conferenceRepository.findAll());
        setSubMenu(createSubMenu());
        return getTemplate();
    }

    @RequestMapping(value="/conference/add", method=RequestMethod.POST)
    public ModelAndView onSubmit(@Validated Conference conference, BindingResult result, RedirectAttributes redirectAttributes){
        addObject("conference", conference).addObject("months", months);
        for(ObjectError e :result.getAllErrors())
            log("chyba", e.toString());
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

    @RequestMapping("/conference/show-all")
    public ModelAndView showAll(){
        setView("show-all");
        title("Výpis všech konferencí").addObject("conferences", conferenceRepository.findAll());
        return getTemplate();
    }

    private HashMap<String, String> createSubMenu(){
        HashMap<String, String> submenu = new HashMap<String, String>();
        submenu.put("/admin/conference/edit", "Editovat");
        submenu.put("/admin/conference/show-all", "Zobrazit vše");
        return submenu;
    }
}
