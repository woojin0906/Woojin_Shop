package kr.spring.config;
// 언제나 쓸 수 있도록 빈 등록

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // 감시 기능

public class AuditaConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl(); // 클래스를 생성해서 AuditorAware로 반환
    }

}
