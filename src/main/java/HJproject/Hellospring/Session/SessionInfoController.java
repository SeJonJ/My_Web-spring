package HJproject.Hellospring.Session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return "세션이 없습니다";
        }

        // 세션 생명 시간 설정
        session.setMaxInactiveInterval(3600);


        log.info("sessionId = {}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessTime={}", new Date(session.getLastAccessedTime()));
        
        return "세션출력";

    }
}
