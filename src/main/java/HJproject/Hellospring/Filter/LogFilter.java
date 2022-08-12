package HJproject.Hellospring.Filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

// HTTP 요청이 들어올 때마다 logFilter 가 먼저 실행됨
@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        // ServletRequest 는 HttpServletRequest 의 부모 클래스
        // 근데 ServletRequest 는 이전꺼라서 다운캐스팅해서 HttpServletRequest 로 만들어야한다
        HttpServletRequest req = (HttpServletRequest) request;

        String uri = req.getRequestURI(); // http 요청 uri 저장
        String uuid = UUID.randomUUID().toString(); // http 요청을 구분하기 위해서 랜덤한 uuid 생성

        try {
            log.info("REQUEST [{}][{}]", uuid, uri);

            // chain 을 사용해서 다음 필터를 불러와야한다.
            // 만약 다음 필터가 존재한다면 해당 필터가 실행되고 아니라면 Servlet 이 호출된다.
            // 반드시!! 필수!!! => 없으면 추가 chain 이던 Servlet 이던 모두 호출이 안된다
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        }finally {
            log.info("RESPONSE [{}][{}]", uuid, uri);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destory");
    }
}
