package kr.spring.config;
// 로그인 보안 관련
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
// 설정 파일로 쓸 수 있다고 알려주는 어노테이션
@Configuration
// 로그인을 하기 위한 설정
@EnableWebSecurity
public class SecurityConfig {

    // 메모리를 미리 올려놔야 하기 때문에 bean 붙이기
    @Bean
    // http 요청에 대한 보안 설정. 페이지 권한, 로그인 페이지, 로그아웃 메소드 설정 예정
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin() // 로그인과 관련된 주소
                .loginPage("/member/login")  // 로그인 주소
                .defaultSuccessUrl("/") // 성공 시 이동할 주소
                .usernameParameter("email") // user이름을 email로 사용할 것이기 때문에 field이름을 적어줘야 함  -> username이라 적은 경우엔 안적어도 됨
                .failureUrl("/member/login/error") // 로그인 실패 시 이동할 페이지
                .and()
                .logout()  // 로그아웃과 관련된 정보
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃을 누를 때 처리할 내용
                .logoutSuccessUrl("/");

        http.authorizeHttpRequests()  // 인증 여부 확인 -> 스프링 3.0 이하 버전은 authorizeRequests()로 설정
                // 스프링 3.0 이하 버전은 antMatchers(), mvcMatchers(), regexMatchers()으로 사용
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() // 페이지 이동할 경우 default로 인증이 걸리도록 되어있기 때문에 추가
                .requestMatchers("/css/**", "/js/**").permitAll()  // 모든 사람에게 css 적용
                .requestMatchers("/", "/member/**", "/item/**", "/images/**").permitAll() // 아무나 페이지에 들어올 수 있고, member, item 밑에 있는 애들은 모두 permit 허용
                .requestMatchers("/admin/**").hasRole("ADMIN") // admin인 애들만 admin에 접속 가능
                .anyRequest().authenticated(); // 인증 받기

        http.exceptionHandling()  // 권한이 없는 경우
                .authenticationEntryPoint(new CustomEntryPoint());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();// 단방향 암호화 객체 생성
    }
}
