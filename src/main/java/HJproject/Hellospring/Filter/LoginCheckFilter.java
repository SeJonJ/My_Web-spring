package HJproject.Hellospring.Filter;

import HJproject.Hellospring.Session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

// 로그인 여부에 따른 페이지 접근 필터
@Slf4j
public class LoginCheckFilter implements Filter {

    // whiteList 생성 => 로그인 상관없이 접근 가능한 페이지
    private static final String[] whiteList = {"/", "/home", "/members/newregisters", "/login", "/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String reqURI = req.getRequestURI();

        HttpServletResponse resp = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 {}", reqURI);

            // 해당 uri 경로를 확인했을 때 만약 whiteList 에 해당하지 않는 경로라면
            // 아래의 로직을 실행
            if (isLoginCheckPath(reqURI)) {
                log.info("인증 체크 로직 실행 {} ", reqURI);
                // 현재 session 을 통해 로그인한 사용자인지 확인
                HttpSession session = req.getSession(false);

                // 만약 세션이 null 이거나 session 안에 담긴 정보가 없다면,
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {} ", reqURI);

                    // 뒤에 redirectURL 을 찍어줌으로써 로그인 후 이전 페이지로 돌아올 수 있게 함
                    // 이전 페이지란 로그인하기 바로전에 간 페이지
                    resp.sendRedirect("/login?redirectURL="+reqURI);
                    return;
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; // 예외 로깅도 가능하지만, 톰캣까지 예외를 보내주어야 함
        }finally {
            log.info("로그인 인증 체크 필터 종료");
        }
    }


    /*
     * 화이트 리스트의 경우 인증 체크 하지 않도록 하는 메서드
     * */
    private boolean isLoginCheckPath(String uri) {
        // spring 에서 제공하는 PatternMatchUtil 을 통해서 uri 가 해당 whiteList 안에 있는지 확인
        // 없다면 false, 있다면 true =>
        return !PatternMatchUtils.simpleMatch(whiteList, uri);
    }
}
