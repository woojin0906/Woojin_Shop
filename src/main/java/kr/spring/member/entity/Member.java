package kr.spring.member.entity;
// 회원 엔티티
import jakarta.persistence.*;
import kr.spring.member.constant.Role;
import kr.spring.member.dto.MemberFormDto;
import kr.spring.utils.entity.BaseEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String address;

    @Enumerated(EnumType.STRING) // 추가를 안하면 열거형은 숫자로 들어감
    private Role role;

    //사용자 만들기 -> 받아온 DTO를 entity로 변경해준다
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        //먼저 Member entity를 만들어준다.
        Member member = new Member();
        member.setName(memberFormDto.getName());  //실제 웹에서 받아올 이름이니까 dto에서 받아온다
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setRole(Role.USER);  //기본으로 다 학생으로 가입

        String password = passwordEncoder.encode(memberFormDto.getPassword());  //일단 비밀번호를 플레인 텍스트로 받아온다. 후 encode를 이용해서 암호화
        member.setPassword(password);  //암호화된 패스워드를 넣는다

        //만든 member 리턴
        return member;
    }
}