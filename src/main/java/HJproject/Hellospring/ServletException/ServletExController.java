package HJproject.Hellospring.ServletException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {

    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("예외 발생!!!");
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse resp) throws IOException {
        resp.sendError(404, "404 Error");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse resp) throws IOException {
        resp.sendError(500, "500 Error");
    }
}
