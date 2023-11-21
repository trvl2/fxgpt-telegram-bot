package tech.fxdev.bot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("/application.properties")
public class BotConfig {

    @Value("${telegram.bot.name}")
    String name;

    @Value("${telegram.bot.token}")
    String token;
}
