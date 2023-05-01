package kr.spring.member.service;

import kr.spring.member.entity.Member;
import kr.spring.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// DB로 값을 가져오거나 controller로 가기 전에 따로 처리해야 할 기능이 있다
@Service
@Transactional
@Log4j2
//final이 붙은 애들을 올려준다 이게 싫으면 @autowired 쓰면 됨
//일반적으로 controller나 service에서 쓰면 상관이 없는데 test에서는 이걸 쓰면 에러가 난다.
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

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

    //implements해놨으니까 추상메소드 구현
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //DB에 있는 email을 찾아서 잡아온다
        Member member = memberRepository.findByEmail(email);

        // Optional을 쓰는 경우
        // findMember가 null이냐를 물어보는 게 아니라, findMember가 존재하는지 안 존재하는지 물어보는 것
        // Optional<Member> findMember = memberRepository.findByEmail(email); -> repository에 Optional로 선언되어 있어야 함.
        // 검사식
        // if(findMember.isPresent()){
        //     throw new UsernameNotFoundException(email);
        // }
        // -> if문 줄여서 쓰기
        // Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 사용자 없음 " + email);

        // 멤버가 없는 경우 (기존에 사용자가 없는 경우)
        if(member == null) {
            //이메일 정보를 추가해서 exception을 날린다. 이거 근데 로그인이 잘못됐다는 예외가 발생한다
            throw new UsernameNotFoundException("해당 사용자가 없습니다." + email);
        }

        log.info("=================> loadUserByUsername : " + member);

        return User.builder()
                .username(member.getEmail())//이메일, 아이디 등 어떤 로직으로 로그인을 하던 그 로그인을 할 필드를 넣어준다.
                .password(member.getPassword()) //암호화 되어서 들어가야 함.
                .roles(member.getRole().toString())//역할을 string으로 넣어줘야 한다. -> enum 안됨
                .build();
    }
}
