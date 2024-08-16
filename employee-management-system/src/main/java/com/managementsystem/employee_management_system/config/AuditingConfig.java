package com.managementsystem.employee_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "customAuditorProvider")
public class AuditingConfig {

    @Bean
    public AuditorAware<String> customAuditorProvider() {
        // Replace "Admin" with actual logic to retrieve the current user.
        return () -> Optional.ofNullable("Admin");
    }
}
