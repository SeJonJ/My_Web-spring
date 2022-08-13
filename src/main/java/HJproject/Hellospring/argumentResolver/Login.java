package HJproject.Hellospring.argumentResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 내가 만드는 사용자 정의 어노테이션
@Target(ElementType.PARAMETER) // 어떤 방식으로 적용할 건지? => 파라미터에 적용
@Retention(RetentionPolicy.RUNTIME) // 동작하는 시간? 동작하기까지 어노테이션이 살아있는 시간? => 즉 런타임까지 어노테이션 정보가 살아있음
public @interface Login {

}
