package conference.controller.user;

import com.sun.javafx.sg.prism.NGShape;
import conference.configuration.FlashMessage;
import conference.model.ExportManager;
import conference.model.entity.Export;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExportController extends UserController{

    @Autowired
    ExportManager exportManager;

    @RequestMapping(value = {"/export/{idArticle}"}, method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("idArticle") String idArticle){
        return title("Export článku")
                .addObject("export", new Export())
                .addObject("patterns", exportManager.getPatterns())
                .addObject("id", idArticle)
                .getTemplate();
    }

    /**
     * Uloží formát do databáze.
     */
    @RequestMapping(value = {"/export/{idArticle}"}, method = RequestMethod.POST)
    public ModelAndView onSubmit(Export export, BindingResult result, RedirectAttributes redirectAttributes){
        title("Export článku").addObject("export", export);
        try{
            exportManager.save(export);
            flashMessage("Úspěšně uloženo", FlashMessage.OK);
            redirect("this");
        }catch (RuntimeException ex){
            flashMessage(ex.getMessage(), FlashMessage.ERROR);
        }
        return getTemplate();
    }

    @RequestMapping(value = {"/export/getfile/{idArticle}"}, method = RequestMethod.POST)
    public ModelAndView getFile(Export export, @PathVariable("idArticle") String idArticle, BindingResult result, RedirectAttributes redirectAttributes){
        log("format", String.valueOf(export.getIdPattern()));
        return getTemplate();
    }
}
