package kr.spring.member.service;

import kr.spring.member.dto.MemberFormDto;
import kr.spring.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;


    // Member Entity 생성
    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("인천광역시 남동구 용현동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
//    Junit의 asserEquals 메소드를 이용하여 요청값과 실제 저장 데이터를 비교
//    assertEquals(기대 값, 실제 저장된 값);
    public void saveMemberTest() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member); // 멤버가 존재하면 예외처리, 없으면 저장된 멤버를 리턴

//        원래 Assertions.assertEquals로 해서 사용해야 하지만, static으로 선언해줘서 그냥 사용할 수 있다.
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
    }

}