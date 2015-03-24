package conference.controller;

import conference.configuration.Constants;
import conference.exceptions.http.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Základní kontroler od kterého musí dědit všechny kontrolery v aplikaci.
 * Šablony musí být ukládány do složky s názvem kontrolleru WEB-INF/pages/název kontroleru/šablona
 * Např.: pages/homepage/index
 */
@Controller
public abstract class BaseController {

    private ModelAndView template;

    private String view = null;

    /**
     * Pokud je nastaven, bude se cesta k šabloně generovat jako pages/module/class/template.jsp
     * Nepovinná položka
     */
    private String module = null;

    public BaseController(){
        this(null);
    }

    public BaseController(String module){
        template = new ModelAndView();
        this.module = module;
        template.addObject("debugMode", Constants.DEBUG_MODE);
        template.addObject("applicationName", Constants.APP_NAME);
    }

    /**
     * Nastaví nadpis stránky v tagu title.
     * @param title
     * @return
     */
    protected BaseController title(String title){
        template.addObject("title", title);
        return this;
    }

    /**
     * Nastavení šablony, která je umístěná ve složce pages (neuvádět složku).
     * @param view
     * @return
     */
    protected BaseController setView(String view){
        this.view = view;
        String path;
        if(module != null)
            path = module+File.separator+getFolderName()+ File.separator+view;
        else path = getFolderName()+ File.separator+view;
        template.setViewName(path); //TODO ověření že soubor existuje
        return this;
    }

    protected String getView(){ return template.getViewName(); }

    protected ModelAndView getTemplate(){
        if(view == null)
            setView("default");
        return template;
    }

    /**
     * Přidá objekt do stránky.
     * @param name
     * @param object
     * @return
     */
    public BaseController addObject(String name, Object object){
        template.addObject(name, object);
        return this;
    }

    /**
     * Přesměruje na zadanou adresu v aplikaci.
     * @param url
     * @return
     */
    public ModelAndView redirect(String url){  return new ModelAndView("redirect:" + url); }

    /**
     * Přesměruje na externí webovou adresu.
     * @param url
     * @return
     * @throws URISyntaxException
     */
    public ResponseEntity<Object> eredirect(String url) throws URISyntaxException {
        url = !url.startsWith("http://") ? "http://" + url : url;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI(url));
        return new ResponseEntity(httpHeaders, HttpStatus.SEE_OTHER);
    }

    /**
     * Vyhodí HTTP výjimku s kódem 404.
     * @param message
     */
    public void error(String message){ throw new BadRequestException(message, 404); }

    /**
     * Vyhodí HTTP výjimku.
     * @param message
     * @param code
     */
    public void error(String message, int code){ throw new BadRequestException(message, code); }

    /**
     * Vrací název složky odvozený od názvu kontroleru.
     * @return
     */
    protected String getFolderName(){
        return this.getClass().getSimpleName().replace("Controller", "").toLowerCase();
    }
}
