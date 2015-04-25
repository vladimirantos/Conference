package conference.controller.user;

import conference.configuration.Constants;
import conference.configuration.FlashMessage;
import conference.model.SearchFactory;
import conference.model.entity.SearchAttributes;
import conference.model.entity.SearchTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.Document;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController extends UserController {

    @Autowired
    private SearchFactory searchFactory;

    @Autowired
    ServletContext context;

    public SearchController() {
        super();
    }

    @RequestMapping(value = {"", "search"}, method = RequestMethod.GET)
    public ModelAndView searchForm() {
        title("Vyhledávání článků").setView("search-form");
        addObject("searchAttributes", new SearchAttributes()).addObject("searchTypes", getSearchTypes());
        return getTemplate();
    }


    @RequestMapping(value = "results", method = RequestMethod.GET)
    public ModelAndView onSubmit(SearchAttributes searchAttributes, BindingResult result, RedirectAttributes redirectAttributes) {
        title("Výsledky vyhledávání").addObject("searchAttributes", searchAttributes).addObject("searchTypes", getSearchTypes());
        List data = searchFactory.setAttributes(searchAttributes).search();
        if (data.size() == 0)
            flashMessage("Nenalezeny žádné záznamy", FlashMessage.INFO);
        else flashMessage("Nalezeno celkem " + data.size() + " záznamů");
        addObject("results", data);
        return setView("results").getTemplate();
    }

    @RequestMapping(value = "download/{conference}/{fileName}", method = RequestMethod.GET)
    public void getPDF(@PathVariable("conference") String conference, @PathVariable("fileName") String fileName, HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String file = Constants.FILE_ROOT + File.separator + conference + File.separator + fileName + ".pdf";
//                response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment;filename="+ Constants.FILE_ROOT + File.separator + conference + File.separator + fileName + ".pdf");

        File downloadFile = new File(file);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        String mimeType = context.getMimeType(file);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        response.setHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName());
        OutputStream outStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outStream.close();
    }

    private Map<Integer, String> getSearchTypes() {
        Map<Integer, String> types = new LinkedHashMap<Integer, String>();
        for (SearchTypes type : SearchTypes.values())
            types.put(type.getId(), type.getName());
        return types;
    }

}
