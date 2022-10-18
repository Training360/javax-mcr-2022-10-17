package auditing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditConfiguration {

    @Bean
    @ConditionalOnMissingBean(PostfixService.class)
    public PostfixService postfixService() {
        return new LengthPostfixService();
    }

    @Bean
    public AuditService auditService(ObjectMapper objectMapper, PostfixService postfixService) {
        return new AuditService(objectMapper, postfixService);
    }
}
