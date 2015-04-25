package conference.controller.user;

import conference.model.SearchFactory;
import conference.model.entity.SearchAttributes;
import conference.model.entity.SearchTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class SearchController extends UserController{

    @Autowired
    private SearchFactory searchFactory;

    public SearchController(){
        super();
    }

    @RequestMapping(value={"", "search"}, method = RequestMethod.GET)
    public ModelAndView searchForm(){
        title("Vyhledávání článků").setView("search-form");
        addObject("searchAttributes", new SearchAttributes()).addObject("searchTypes", getSearchTypes());
        return getTemplate();
    }


    @RequestMapping(value="results", method=RequestMethod.GET)
    public ModelAndView onSubmit(SearchAttributes searchAttributes, BindingResult result, RedirectAttributes redirectAttributes){
        title("Výsledky vyhledávání").addObject("searchAttributes", searchAttributes);
        addObject("results", searchFactory.setAttributes(searchAttributes).search());
        return setView("results").getTemplate();
    }

    private Map<Integer, String> getSearchTypes(){
        Map<Integer, String> types = new LinkedHashMap<Integer, String>();
        for(SearchTypes type : SearchTypes.values())
            types.put(type.getId(), type.getName());
        return types;
    }

}
