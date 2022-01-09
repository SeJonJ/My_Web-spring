package HJproject.Hellospring.Controller;

import HJproject.Hellospring.domain.Member;
import HJproject.Hellospring.service.memberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final memberService memberService;

    @Autowired
    // Bean 으로 설정되었던 memberService 를 넣어줌. 의존성 주입
    public MemberController(memberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass()); // AoP 프록시 동작 확인하기
    }


    @GetMapping("/members/newregisters") // http 에서 get 으로 넘어올때 -> 주로 값을 출력하는 경우
    public String createForm(){
        return "members/newregisters";
    }

    @PostMapping("/members/new") // http 에서 members/new 페이지에 대해 post 로 넘어올 때 -> 주로 값을 등록하는 경우
    public String create(MemberForm form){
        Member member = new Member(); // member 객체 생성
        member.setName(form.getName()); // member name 에 form 에서 받아온 name 을 넣어준다
        member.setId(form.getUserid());
        member.setPasswd(form.getUserpw());
        member.setSex(form.getSex());
        member.setEmail(form.getEmail());
        member.setEmaddress(form.getEmaddress());

        memberService.join(member);
        //member service 를 사용해 member 객체에 대해 join 메서드를 실행한다.

//        System.out.println("member : "+member.getName());
//        System.out.println("member : "+member.getPasswd());
//        System.out.println("member : "+member.getId());


        return "redirect:/"; // 회원가입이 끝나서 가입하기를 누르면 home(root page) 으로 설정된 페이지로 돌아감
    }

    @GetMapping("/members/member_List") // members 페이지에 대해 Get 으로 넘어올 때 -> 주로 값을 불러오는 하는 경우
    public String list(Model model){ // Model 사용을 list 로 사용
        List<Member> members = memberService.findMembers(); // memberService.findMembers() 를 list 형식으로 저장
        model.addAttribute("members", members);
        // 파라미터로 넘어온 members 을 model에 담아서 members/members_list 의 members 로 넘긴다.

        return "members/members_List"; // return 시에는 앞에  " / " 가 없어야함
    }
}