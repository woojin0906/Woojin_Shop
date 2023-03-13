package kr.spring.member.dto;
// 회원가입 화면으로부터 넘어오는 가입정보를 담을 Dto
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식으로 입력하세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    @Length(min = 4, max = 12, message = "최소 4자, 최대 12자를 입력하세요.")
    private String password;

    @NotEmpty(message = "주소는 필수 항목입니다.")
    private String address;

}
