package HJproject.Hellospring.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        // preHandle 뒤에오는 인터셉터 핸들러에서 uuid 를 동일한 사용하기 위해서
        request.setAttribute(LOG_ID, uuid);

        // 컨트롤러에서 @RequestMapping 처럼 사용하는 경우 HandlerMethod 를 사용한다.
        // 이쪽은 Controller 의 Handler 과 HandlerAdepter 와 관련있다
        // 정적 리소스 : ResourceHttpRequestHandler

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler; // 호출할 컨트롤러 메서드의 모든 정보가 포함된다
        }

        log.info("REQUEST [{}] [{}] [{}] ", uuid, reqURI, handler);
        return true;
    }

    // postHandle 는 ModelAndView 를 매개변수로 받는다
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandler [{}]", modelAndView);
    }

    // afterCompletion 은 Exception 를 매개변수로 받는다
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String reqURI = request.getRequestURI();
        String logId = (String)request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}] [{}] [{}]", logId, reqURI, handler);
        if (ex != null) {
            log.error("aferCompletion Error!!!!", ex);
        }
    }
}
