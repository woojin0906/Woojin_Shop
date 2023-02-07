package kr.spring.member.service;

import kr.spring.member.entity.Member;
import kr.spring.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// DB로 값을 가져오거나 controller로 가기 전에 따로 처리해야 할 기능이 있다
@Service
@Transactional
//final이 붙은 애들을 올려준다 이게 싫으면 @autowired 쓰면 됨
//일반적으로 controller나 service에서 쓰면 상관이 없는데 test에서는 이걸 쓰면 에러가 난다.
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        // 중복확인
        validateDuplicate(member);
        // 중복확인 후 회원 저장
        return memberRepository.save(member);
    }

    private void validateDuplicate(Member member) {
        //이메일로 찾았을 때 값이 있는지 없는지 판단
        Member findMember = memberRepository.findByEmail(member.getEmail());
        // DB에서 이메일이 검색이 되면 이미 등록되어있는 회원이라는 알림 표시
        if(findMember != null) {
            throw new IllegalStateException("이미 등록된 사용자 입니다.");
        }

    }
}
