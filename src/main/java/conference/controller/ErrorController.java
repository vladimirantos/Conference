package conference.controller;

import conference.configuration.Constants;
import conference.exceptions.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorController extends BaseController implements HandlerExceptionResolver{

    @RequestMapping("/404")
    public void NotFound404(){
        throw new NotFoundException();
    }

    @RequestMapping("/502")
    public void badGateway502(){
        throw new BadGatewayException();
    }

    @RequestMapping("/403")
    public void forbidden403(){
        throw new ForbiddenException();
    }

    @RequestMapping("/401")
    public void unauthorized401(){
        throw new UnauthorizedException();
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if(e instanceof BadRequestException){
            BadRequestException ex = (BadRequestException)e;
            if(Constants.DEBUG_MODE)
                setView("debugHttpErrorPage");
            else
                setView(String.valueOf(ex.getCode()));
        }else {
            if (Constants.DEBUG_MODE)
                setView("debugErrorPage");
            else setView("error");
        }
        addObject("exception", e);
        addObject("url", httpServletRequest.getRequestURL());
        return getTemplate();
    }
}
