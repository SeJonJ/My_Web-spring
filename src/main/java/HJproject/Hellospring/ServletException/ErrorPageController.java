package HJproject.Hellospring.ServletException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class ErrorPageController {

    @RequestMapping("/error-page/404")
    public String error404(HttpServletRequest req, HttpServletResponse resp) {
        log.info("errorPage 404");
        printErrorInfo(req);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String error500(HttpServletRequest req, HttpServletResponse resp) {
        log.info("errorPage 500");
        printErrorInfo(req);
        return "error-page/500";
    }

    private void printErrorInfo(HttpServletRequest req) {
        log.info("dispatchTypes= {}", req.getDispatcherType());
    }
}
