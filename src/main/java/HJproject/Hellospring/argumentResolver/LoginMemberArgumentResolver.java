package HJproject.Hellospring.argumentResolver;

import HJproject.Hellospring.Session.SessionConst;
import HJproject.Hellospring.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("############# LoginMemberArgumentResolver 실행 ############");

        // hasParameterAnnotation 는 해당 메서드의 매개변수로 오는 어노테이션이 붙어있는지 여부를 반환
        // 붙어있으면 true, 아니면 false
        boolean hasLoginAnno = parameter.hasParameterAnnotation(Login.class);

        // 파라미터의 클래스 타입이 isAssignableFrom 클래스와 매개변수에 해당하는 arameter.getParameterType() 가
        // 동일한 클래스(타입)인지 여부 확인 => true, false
        boolean isMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        // hasLoginAnno 와 isMemberType 가 모두 true 일 때만 아래의 resolveArgument 가 실행됨
        return hasLoginAnno && isMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("############# resolveArgument 실행 ############");

        // HttpServletRequest 를 가져와야함
        HttpServletRequest req = (HttpServletRequest)webRequest.getNativeRequest();

        // HttpServletRequest 에서 세션을 가져왔을 때 null 이면 그대로 null 을 return
        HttpSession session = req.getSession();
        if(session == null){
            return null;
        }
        // 세션이 있다면 해당 세션의 내용을 return
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
