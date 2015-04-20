package conference.controller.administration;

import conference.configuration.FlashMessage;
import conference.exceptions.ApplicationException;
import conference.model.ArticleManager;
import conference.model.entity.Article;
import conference.model.entity.Conference;
import conference.model.repository.ConferenceRepository;
import conference.validator.ArticleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ArticleController extends AdminController {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ArticleManager articleManager;

    @Autowired
    @Qualifier("articleValidator")
    private ArticleValidator articleValidator;

    public ArticleController() {
        super();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringMultipartFileEditor());
        binder.setValidator(articleValidator);
    }

    @RequestMapping(value = "/article/add", method = RequestMethod.GET)
    public ModelAndView initForm() {
        setView("add");
        title("Vkládání článků");
        addObject("article", new Article()).addObject("conf", getConferences());
        return getTemplate();
    }

    @RequestMapping(value = "article/upload", method = RequestMethod.POST)
    public ModelAndView onSubmit(@ModelAttribute("article") Article article, BindingResult result, RedirectAttributes redirectAttributes)
            throws IOException {
        addObject("article", article);
        if (result.hasErrors()) {
            setView("add");
            flashMessage("Došlo k chybě při odesílání formuláře", FlashMessage.ERROR);
        } else {
            try {
                articleManager.setArticle(article).saveConfigFile();
                flashMessage("Konfigurační soubor byl úspěšně přidán");
            }catch (ApplicationException ex){
                log("VÝJIMKA", ex.getMessage());
                flashMessage(ex.getMessage(), FlashMessage.ERROR);
            }catch (RuntimeException ex) {
                log("VÝJIMKA", ex.getMessage());
                flashMessage("Chyba při ukládání konfiguračního souboru", FlashMessage.ERROR);
            }
            return redirect("/admin/article/add");
        }
        return getTemplate();
    }

    @RequestMapping(value = "article/uploadArticles", method = RequestMethod.POST)
    public ModelAndView onSubmitUploadArticles(@ModelAttribute("article") Article article, BindingResult result, RedirectAttributes redirectAttributes)
            throws IOException {
        addObject("article", article);
        if (result.hasErrors()) {
            setView("add");
            flashMessage("Došlo k chybě při odesílání formuláře", FlashMessage.ERROR);
        } else {
            try {
                articleManager.setArticle(article).saveArticles();
                flashMessage("Bylo nahráno celkem "+article.getArticles().size() + " souborů");
            }catch (ApplicationException ex){
                log("VÝJIMKA", ex.getMessage());
                flashMessage(ex.getMessage(), FlashMessage.ERROR);
            }catch (RuntimeException ex) {
                log("VÝJIMKA", ex.getMessage());
                flashMessage("Chyba při ukládání článků", FlashMessage.ERROR);
            }
            return redirect("/admin/article/add");
        }
        return getTemplate();
    }

    /**
     * @return Vrací seznam konferencích v kolekci typu id_konference - název
     */
    private Map<Long, String> getConferences() {
        List<Conference> conferences = conferenceRepository.findAll();
        Map<Long, String> result = new LinkedHashMap<Long, String>();
        for (Conference c : conferences)
            result.put(c.getId(), c.getName());
        return result;
    }
}
