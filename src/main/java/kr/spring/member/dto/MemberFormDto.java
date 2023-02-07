package kr.spring.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberFormDto {
    private String name;
    private String email;
    private String password;
    private String address;

}
