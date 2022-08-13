package HJproject.Hellospring;

import HJproject.Hellospring.Filter.LogFilter;
import HJproject.Hellospring.Filter.LoginCheckFilter;
import HJproject.Hellospring.argumentResolver.LoginMemberArgumentResolver;
import HJproject.Hellospring.interceptor.LogInterceptor;
import HJproject.Hellospring.interceptor.LoginCheckInterceptor;
import HJproject.Hellospring.repository.*;
import HJproject.Hellospring.service.memberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.List;

@Configuration // 스프링 빈에 등록하기 위한 설정파일이라는 Annotation
public class SpringConfig implements WebMvcConfigurer {

    // ArgumentResolver 를 등록하기 위한 override
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1) // 인터셉터 순서
                .addPathPatterns("/**") // 패턴 적용 url
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 패턴 예외 url

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2) // 인터셉터 순서
                .addPathPatterns("/**") // 패턴을 적용하고자 하는 url
                // 패턴 예외 url
                .excludePathPatterns("/**/js/*.js", "/index", "/", "/home", "/members/newregisters", "/login", "/logout","/css/**", "/*.ico", "/error");

    }

    // Servlet Filter 를 사용하기 위한 Bean
    //@Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<Filter>();
        filterBean.setFilter(new LogFilter());

        // 필터 순서 => 필터 체인 시 사용되는 순서
        filterBean.setOrder(1);

        // 필수 적용 URL => 필터 적용 시 사용되는 url : /* 라면 모든 url 에 적용됨
        filterBean.addUrlPatterns("/*");

        return filterBean;
    }

    //@Bean
    public FilterRegistrationBean loginCheckFilterBean(){
        FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<Filter>();
        filterBean.setFilter(new LoginCheckFilter());

        // 필터 순서 => 필터 체인 시 사용되는 순서
        // 이전 logFilter 가 살아있음으로 2번
        filterBean.setOrder(2);

        // 필수 적용 URL => 필터 적용 시 사용되는 url : /* 라면 모든 url 에 적용됨
        filterBean.addUrlPatterns("/*");

        return filterBean;
    }

    private DataSource dataSource;

    @PersistenceContext // EntityManager 을 사용하기 위한 어노테이션
    EntityManager em;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean // Bean 에 등록되어야하는 객체라는 의미의 Annotation
    public memberService memberService() {
        return new memberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
        return new JpamemberRepository(em);
    }

//    // 아래처럼 Bean 으로 등록하거나 @Component 어노테이션 사용
//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }

}
