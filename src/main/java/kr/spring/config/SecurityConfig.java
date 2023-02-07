package kr.spring.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//설정 파일로 쓸 수 있다고 알려주는 어노테이션
@Configuration
//로그인을 하기 위한 설정
@EnableWebSecurity
public class SecurityConfig {

    //    메모리를 미리 올려놔야 하기 때문에 bean 붙이기
    @Bean
    //  http 요청에 대한 보안 설정. 페이지 권한, 로그인 페이지, 로그아웃 메소드 설정 예정
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();// 단방향 암호화 객체 생성
    }
}
