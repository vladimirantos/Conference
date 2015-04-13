package conference.controller;

import conference.configuration.Constants;
import conference.configuration.FlashMessage;
import conference.exceptions.http.BadRequestException;
import conference.model.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Základní kontroler od kterého musí dědit všechny kontrolery v aplikaci.
 * Šablony musí být ukládány do složky s názvem kontrolleru WEB-INF/pages/název kontroleru/šablona
 * Např.: pages/homepage/index
 * Metoda setView instancuje ModelAndView a musí být volána jako první.
 * TODO: všechna nastavení se budou provádět do zvláštních proměnnných a do ModelAndView se budou předávat až v getTemplate.
 */
@Controller
public abstract class BaseController{

    private static String DEFAULT_VIEW_NAME = "default";

    private String title;

    private String view;

    private String viewPath;

    private String module;

    private ModelAndView template;

    private LinkedHashMap<String, Object> attributes;

    public BaseController(String module){
        this.module = module;
        attributes = new LinkedHashMap<String, Object>();
        addObject("debugMode", Constants.DEBUG_MODE)
                .addObject("applicationName", Constants.APP_NAME)
                .addObject("module", this.module);
    }

    public BaseController(){
        this(null);
    }

    public BaseController title(String title){
        this.title = title;
        return this;
    }

    public BaseController setView(String view){
        this.view = view;
        String path;
        if(module != null)
            viewPath = module+File.separator+getFolderName()+ File.separator+view;
        else viewPath = getFolderName()+ File.separator+view;
        return this;
    }

    /**
     * Vytvoří zadaný typ flash message.
     * @param message
     * @param messageType
     */
    public BaseController flashMessage(String message, String messageType){
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
    public BaseController flashMessage(String message){
        return flashMessage(message, FlashMessage.OK);
    }

    /**
     * Vymaže flash message po jednom přesměrování.
     */
    public BaseController removeFlashMessage(){
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

    public String getView(){
        return view;
    }

    public String getViewPath(){
        return viewPath;
    }

    public BaseController addObject(String key, Object value){
        attributes.put(key, value);
        return this;
    }

    /**
     * Vytvoří submenu se zadanými prvky. Klíče jsou odkazy a hodnoty jsou texty elementů odkazu.
     * @param elements
     * @return
     */
    public BaseController setSubMenu(Map<String, String> elements){
        addObject("submenu", elements);
        return this;
    }

    public ModelAndView getTemplate(){
        ModelAndView template = new ModelAndView();
        addObject("title", title);
        for(Map.Entry<String, Object> entry : attributes.entrySet()){
            template.addObject(entry.getKey(), entry.getValue());
        }

        if(view == null)
            setView(DEFAULT_VIEW_NAME);
        template.setViewName(viewPath);
        removeFlashMessage();
        return template;
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

    /**
     * Přesměruje na zadanou adresu v aplikaci. Pokud je parametr nastaven na this, přesměruje se na aktuální adresu.
     * @param url
     * @return ModelAndView
     */
    public ModelAndView redirect(String url){
        dispose();
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
    private String getFolderName(){
        return this.getClass().getSimpleName().replace("Controller", "").toLowerCase();
    }

    /**
     * Vymaže celý obsah BaseControlleru. Použije se při redirectu.
     */
    private void dispose(){
        title = null;
        view = null;
        viewPath = null;
        template = null;
    }
}

//public abstract class BaseController {
//
//    private ModelAndView template;
//
//    private String view = null;
//
//    /**
//     * Pokud je nastaven, bude se cesta k šabloně generovat jako pages/module/class/template.jsp
//     * Nepovinná položka
//     */
//    private String module = null;
//
//    public BaseController(){
//        this(null);
//    }
//
//    /**
//     * Konstruktor se zadaným modulem. Modul je hlavní složka v šablonách (WEB-INF/pages)
//     * @param module
//     */
//    public BaseController(String module){
//        this.module = module;
//    }
//
//    /**
//     * Vytvoří zadaný typ flash message.
//     * @param message
//     * @param messageType
//     */
//    protected BaseController flashMessage(String message, String messageType){
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
//        request.getSession().setAttribute("messages", message);
//        request.getSession().setAttribute("mtype", messageType);
//        request.getSession().setAttribute("count", 1);
//        log(request.getSession().getAttribute("messages").toString());
//        return this;
//    }
//
//    /**
//     * Vytvoří OK flash message.
//     * @param message
//     */
//    protected BaseController flashMessage(String message){
//        return flashMessage(message, FlashMessage.OK);
//    }
//
//    /**
//     * Vymaže flash message po jednom přesměrování.
//     */
//    protected BaseController removeFlashMessage(){
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
//        Object o = request.getSession().getAttribute("count");
//        if(o != null){
//            int count = Integer.parseInt(o.toString());
//            if(count == 0) {
//                HttpSession session = request.getSession();
//                session.removeAttribute("messages");
//                session.removeAttribute("mtype");
//                session.removeAttribute("count");
//            }else{
//                request.getSession().setAttribute("count", 0);
//            }
//        }
//        return this;
//    }
//
//
//    protected BaseController title(String title){
//        if(template == null)
//            template = new ModelAndView();
//        template.addObject("title", title);
//        removeFlashMessage();
//        return this;
//    }
//
//    /**
//     * Nastavení šablony, která je umístěná ve složce pages (neuvádět složku).
//     * @param view
//     * @return
//     */
//    protected BaseController setView(String view){
//        this.view = view;
//        template = new ModelAndView();
//        String path;
//        if(module != null)
//            path = module+File.separator+getFolderName()+ File.separator+view;
//        else path = getFolderName()+ File.separator+view;
//        template.setViewName(path); //TODO ověření že soubor existuje
//        return this;
//    }
//
//    protected String getView(){ return template.getViewName(); }
//
//    protected ModelAndView getTemplate(){
//        log("VIEW", view);
//        if(view == null)
//            setView("default");
//        template.addObject("debugMode", Constants.DEBUG_MODE);
//        template.addObject("applicationName", Constants.APP_NAME);
//        template.addObject("module", module);
//        return template;
//    }
//
//    /**
//     * Přidá objekt do stránky.
//     * @param name
//     * @param object
//     * @return
//     */
//    public BaseController addObject(String name, Object object){
//        if(template == null)
//            template = new ModelAndView();
//        template.addObject(name, object);
//        return this;
//    }
//
//    /**
//     * Vytvoří submenu se zadanými prvky. Klíče jsou odkazy a hodnoty jsou texty elementů odkazu.
//     * @param elements
//     * @return
//     */
//    protected BaseController setSubMenu(Map<String, String> elements){
//        addObject("submenu", elements);
//        return this;
//    }
//
//    /**
//     * Zobrazí zadanou zprávu do logu.
//     * @param title
//     * @param message
//     * @return
//     */
//    public BaseController log(String title, String message){
//        Log.message(title, message, this);
//        return this;
//    }
//
//    public BaseController log(String message){
//        log("Hlášení", message);
//        return this;
//    }
//
//    public BaseController log(String title, Map messages){
//        Log.message(title, messages, this);
//        return this;
//    }
//
//    public BaseController log(Map messages){
//        log("Hlášení", messages);
//        return this;
//    }
//
//    /**
//     * Přesměruje na zadanou adresu v aplikaci. Pokud je parametr nastaven na this, přesměruje se na aktuální adresu.
//     * @param url
//     * @return ModelAndView
//     */
//    public ModelAndView redirect(String url){
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
//        if(url.equals("this"))
//            url = request.getRequestURI().toString();
//        ModelAndView tmp = new ModelAndView("redirect:"+url);
//        return tmp;
//    }
//
//    /**
//     * Přesměruje na aktuální adresu.
//     * @return ModelAndView
//     */
//    public ModelAndView redirect(){
//        return redirect("this");
//    }
//
//    /**
//     * Přesměruje na externí webovou adresu.
//     * @param url
//     * @return
//     * @throws URISyntaxException
//     */
//    public ResponseEntity<Object> eredirect(String url) throws URISyntaxException {
//        url = !url.startsWith("http://") ? "http://" + url : url;
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(new URI(url));
//        return new ResponseEntity(httpHeaders, HttpStatus.SEE_OTHER);
//    }
//
//    /**
//     * Vyhodí HTTP výjimku s kódem 404.
//     * @param message
//     */
//    public void error(String message){ throw new BadRequestException(message, 404); }
//
//    /**
//     * Vyhodí HTTP výjimku.
//     * @param message
//     * @param code
//     */
//    public void error(String message, int code){ throw new BadRequestException(message, code); }
//
//    /**
//     * Vrací název složky odvozený od názvu kontroleru.
//     * @return
//     */
//    protected String getFolderName(){
//        return this.getClass().getSimpleName().replace("Controller", "").toLowerCase();
//    }
//}
