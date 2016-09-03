package pl.kielce.tu.worldyouthday.utils;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import pl.kielce.tu.worldyouthday.mail.MailService;

@Profile("test")
@Configuration
public class MailServiceMock {
    @Bean
    @Primary
    public MailService mailService() {
        return Mockito.mock(MailService.class);
    }
}
