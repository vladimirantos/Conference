package conference.controller;

import conference.configuration.Constants;
import conference.configuration.FlashMessage;
import conference.exceptions.http.BadRequestException;
import conference.model.Log;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Základní kontroler od kterého musí dědit všechny kontrolery v aplikaci.
 * Šablony musí být ukládány do složky s názvem kontrolleru WEB-INF/pages/název kontroleru/šablona
 * Např.: pages/homepage/index
 */
@Controller
public abstract class BaseController {
    protected static final Logger logger = Logger.getLogger(BaseController.class);
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

    /**
     * Konstruktor se zadaným modulem. Modul je hlavní složka v šablonách (WEB-INF/pages)
     * @param module
     */
    public BaseController(String module){
        template = new ModelAndView();
        this.module = module;
        template.addObject("debugMode", Constants.DEBUG_MODE);
        template.addObject("applicationName", Constants.APP_NAME);
        template.addObject("module", module);
    }

    /**
     * Vytvoří zadaný typ flash message.
     * @param message
     * @param messageType
     */
    protected BaseController flashMessage(String message, String messageType){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        request.getSession().setAttribute("messages", message);
        request.getSession().setAttribute("mtype", messageType);
        request.getSession().setAttribute("count", 1);
        return this;
    }

    /**
     * Vytvoří OK flash message.
     * @param message
     */
    protected BaseController flashMessage(String message){
        return flashMessage(message, FlashMessage.OK);
    }

    /**
     * Vymaže flash message po jednom přesměrování.
     */
    protected BaseController removeFlashMessage(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        Object o = request.getSession().getAttribute("count");
        if(o != null){
            int count = Integer.parseInt(o.toString());
            if(count == 0) {
                HttpSession session = request.getSession();
                session.removeAttribute("messages");
                session.removeAttribute("mtype");
                session.removeAttribute("count");
            }else{
                request.getSession().setAttribute("count", 0);
            }
        }
        return this;
    }

    /**
     * Nastaví nadpis stránky v tagu title.
     * @param title
     * @return
     */
    protected BaseController title(String title){
        template.addObject("title", title);
        removeFlashMessage();
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
     * Zobrazí zadanou zprávu do logu.
     * @param title
     * @param message
     * @return
     */
    public BaseController log(String title, String message){
        Log.message(title, message, this);
        return this;
    }

    public BaseController log(String message){
        log("Hlášení", message);
        return this;
    }

    public BaseController log(String title, Map messages){
        Log.message(title, messages, this);
        return this;
    }

    public BaseController log(Map messages){
        log("Hlášení", messages);
        return this;
    }

    public BaseController log(String title, Boolean message){
        Log.message(title, message, this);
        return this;
    }

    public BaseController log(Boolean message){
        Log.message("Hlášení", message, this);
        return this;
    }
    /**
     * Přesměruje na zadanou adresu v aplikaci. Pokud je parametr nastaven na this, přesměruje se na aktuální adresu.
     * @param url
     * @return ModelAndView
     */
    public ModelAndView redirect(String url){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        if(url.equals("this"))
            url = request.getRequestURI().toString();
        ModelAndView tmp = new ModelAndView("redirect:"+url);
        return tmp;
    }

    /**
     * Přesměruje na aktuální adresu.
     * @return ModelAndView
     */
    public ModelAndView redirect(){
        return redirect("this");
    }

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
