package conference.controller.user;

import com.sun.javafx.sg.prism.NGShape;
import conference.configuration.FlashMessage;
import conference.model.ArticleManager;
import conference.model.ArticleParser;
import conference.model.ExportManager;
import conference.model.entity.DbArticle;
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

    @Autowired
    ArticleManager articleManager;

    @RequestMapping(value = {"/export/{idArticle}"}, method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("idArticle") String idArticle){
        Export export = new Export();
        export.setArticle(Integer.parseInt(idArticle));
        return title("Export článku")
                .addObject("export", export)
                .addObject("patterns", exportManager.getPatterns())
                .addObject("id", idArticle)
                .getTemplate();
    }

    /**
     * Uloží formát do databáze.
     */
    @RequestMapping(value = {"/export/{idArticle}"}, method = RequestMethod.POST)
    public ModelAndView onSubmit(Export export, BindingResult result, RedirectAttributes redirectAttributes) {
        title("Export článku").addObject("export", export).addObject("result", null);

        if(export.getArticle() != 0){ //exportování článku
            log("XXX", String.valueOf(export.getArticle()));

            return redirect("this");
        }

        //uložení formátu do databáze
        try{
            exportManager.save(export);
            flashMessage("Úspěšně uloženo", FlashMessage.OK);
            return redirect("this");
        }catch(RuntimeException ex){
            flashMessage(ex.getMessage());
        }
        return getTemplate();
    }

    @RequestMapping(value="/export/file", method=RequestMethod.POST)
    public ModelAndView getFile(Export export, BindingResult result, RedirectAttributes redirectAttributes){
        addObject("export", export);
        if(export.getIdPattern() == 0){
            flashMessage("Nebyl vybrán formát", FlashMessage.ERROR);
            return redirect("/search/export/"+export.getArticle());
        }
        String pattern = exportManager.getPatternById(export.getIdPattern()).getPattern();
        DbArticle article = articleManager.getArticleById(export.getArticle());
        ArticleParser articleParser = new ArticleParser(pattern, article);
        addObject("result", articleParser.replace());

        return redirect("/search/export/"+export.getArticle());
    }
}
