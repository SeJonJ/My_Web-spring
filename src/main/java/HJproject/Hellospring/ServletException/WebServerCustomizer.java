package HJproject.Hellospring.ServletException;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

// WebServerFactoryCustomizer<ConfigurableWebServerFactory> 인터페이스를 구현하여 커스텀 에러 페이지를 설정할 수 있다
// 만약 404, 500, runtimeException 등이 발생한다면
// WAS 에서는 ErrorPage 에서 설정한 HttpStatus 코드와 함께 Path 위치로 가도록 다시 http 요청을 실행한다.
// 따라서 path 에 해당하는 요청을 처리할 컨트롤러가 필요하다

@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // 404 error
        ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");

        // 500 error
        ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

        // runtime Exception 및 runtime Exception 의 자식 예외도 포함
        ErrorPage exPage = new ErrorPage(RuntimeException.class, "/error-page/500");

        factory.addErrorPages(error404, error500, exPage);
    }
}
