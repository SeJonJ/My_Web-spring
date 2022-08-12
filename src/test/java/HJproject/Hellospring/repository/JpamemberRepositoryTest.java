package HJproject.Hellospring.repository;

import HJproject.Hellospring.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.Optional;

@SpringBootTest
class JpamemberRepositoryTest {

    @Autowired
    MemberRepository mp;


    @Test
    void findById() {
        Optional<Member> member = mp.findById("admin");
        System.out.println(member);
    }
}