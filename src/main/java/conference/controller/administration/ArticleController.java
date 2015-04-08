package conference.controller.administration;

import conference.configuration.FlashMessage;
import conference.model.ArticleManager;
import conference.model.Log;
import conference.model.entity.Article;
import conference.model.entity.Conference;
import conference.model.entity.Data;
import conference.model.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ArticleController extends AdminController{

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ArticleManager articleManager;

    public ArticleController(){
        super();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringMultipartFileEditor());
    }

    @RequestMapping(value="/article/add", method = RequestMethod.GET)
    public ModelAndView initForm() {
        setView("add");
        title("Vkládání článků")
                .addObject("article", new Article())
                .addObject("conf", getConferences());
        return getTemplate();
    }


    @RequestMapping(value="article/upload", method = RequestMethod.POST)
    public ModelAndView onSubmit(@ModelAttribute("article") Article article, BindingResult result) throws IOException {
        addObject("article", article);
        MultipartFile multipartFile = article.getConfigFile();
        log("AHOJ", multipartFile == null ? "null" : "neni nul");
        article.getConfigFile().transferTo(new File("C:\\Users\\Vladimír\\Documents\\" + article.getConfigFile().getOriginalFilename()));
        for(ObjectError e : result.getAllErrors())
            Log.message("ERROR", e.toString(), this);
        if(result.hasErrors()){
            setView("add");
            flashMessage("Nepodařilo se odeslat formulář", FlashMessage.ERROR);
        } else {
            try{
                flashMessage("Konference byla úspěšně přidána");
                return redirect("/admin/article/add");
            }catch(RuntimeException ex){
                flashMessage(ex.getMessage(), FlashMessage.ERROR);
            }
        }
        return getTemplate();
    }

    /**
     * @return Vrací seznam konferencích v kolekci typu id_konference - název
     */
    public Map<Long, String> getConferences(){
        List<Conference> conferences = conferenceRepository.findAll();
        Map<Long, String> result = new LinkedHashMap<Long, String>();
        for(Conference c : conferences)
            result.put(c.getId(), c.getName());
        return result;
    }
}
