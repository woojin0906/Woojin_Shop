package kr.spring.test.dto;
// dto 테스트
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TestDto {

    private String name;
    private int age;

}
