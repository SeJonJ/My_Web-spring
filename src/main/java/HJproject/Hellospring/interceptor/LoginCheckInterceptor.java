package HJproject.Hellospring.interceptor;

import HJproject.Hellospring.Session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 스프링 인터셉터는 서블릿 필터에서 사용하던 화이트 리스트를 따로 작성할 필요가 없다!
// ==> 단, SpringConfig 에 추후 Interceptor 을 등록할때 특정 url 을 등록하면 된다.
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행!! {}", reqURI);

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");

            // 로그인으로 redirect
            response.sendRedirect("/login?redirectURL="+reqURI);
            return false;
        }

        return true;
    }
}
